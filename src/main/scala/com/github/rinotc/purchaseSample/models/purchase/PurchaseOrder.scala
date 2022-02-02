package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.money.Money
import com.github.rinotc.purchaseSample.models.part.Part
import com.github.rinotc.purchaseSample.models.{Aggregate, Entity}

import java.util.Currency

/**
 * 購入注文
 *
 * @param id 購入注文ID
 * @param approvedLimit 承認限度額
 * @param orderItems 注文品目のリスト
 */
final class PurchaseOrder private (
    val id: PurchaseOrderId,
    val approvedLimit: Money,
    val orderItems: Seq[PurchaseOrderLineItem]
) extends Entity[PurchaseOrderId]
    with Aggregate {

  import PurchaseOrder._

  invariant()

  // この注文の通貨単位
  private lazy val thisOrderCurrency = approvedLimit.currency

  /** 注文総額 */
  def total: Money = calcTotal(orderItems, thisOrderCurrency)

  /**
   * 新しい購入注文の追加
   *
   * @param item     新たに購入するアイテム
   * @param quantity 量
   * @return 新たに購入するアイテムを含めた注文総額が承認限度額より大きい時はエラーメッセージを返す。
   *         承認限度額以下であるならば、新たに購入するアイテムを含めた新たな購入注文インスタンスを
   *         返す。
   */
  def addPurchaseItem(item: Part, quantity: Int): Either[String, PurchaseOrder] = {
    val newLineItemId               = PurchaseOrderLineItemId(maxPurchaseOrderLineItemIdValue + 1)
    val newLineItem                 = PurchaseOrderLineItem(newLineItemId, item, quantity)
    val orderItemsAfterAddedNewItem = orderItems.+:(newLineItem)
    val calcTotalAfterAddedNewItem  = calcTotal(orderItemsAfterAddedNewItem, thisOrderCurrency)
    if (calcTotalAfterAddedNewItem <= approvedLimit) {
      Right(apply(id, approvedLimit, orderItemsAfterAddedNewItem))
    } else {
      Left(s"$MustLessThanEqualApprovedLimitMessage, 新アイテム追加後総額($calcTotalAfterAddedNewItem), 承認限度額($approvedLimit)")
    }
  }

  // 購入注文品目のIDの最大の値
  private def maxPurchaseOrderLineItemIdValue: Int = orderItems.map(_.id.value).maxOption.getOrElse(1)

  override def canEqual(other: Any): Boolean = other.isInstanceOf[PurchaseOrder]

  override def toString =
    s"PurchaseOrder(thisOrderCurrency=$thisOrderCurrency, id=$id, approvedLimit=$approvedLimit, orderItems=$orderItems, total=$total)"

  // クラス不変表明
  private def invariant(): Unit = {
    require(
      mustLessEqualThanApprovedLimit(total, approvedLimit),
      s"$MustLessThanEqualApprovedLimitMessage, 総額($total), 承認限度額($approvedLimit) "
    )
    require(mustNotDuplicateOrderLineItemId, "購入注文品目のIDは集約内部で重複しない")
  }

  private def mustNotDuplicateOrderLineItemId: Boolean =
    orderItems.groupBy(_.id).forall { case (_, group) => group.size == 1 }
}

object PurchaseOrder {
  // 注文総額は承認限度額以下でなくではならない
  private def mustLessEqualThanApprovedLimit(total: Money, approvedLimit: Money): Boolean = total <= approvedLimit

  private val MustLessThanEqualApprovedLimitMessage = "注文総額は承認限度額以下でなくてはならない"

  private def calcTotal(orderItems: Seq[PurchaseOrderLineItem], thisOrderCurrency: Currency): Money =
    orderItems.map(_.totalAmount).foldLeft(Money(0, thisOrderCurrency)) { (m1, m2) => m1 plus m2 }

  def apply(id: PurchaseOrderId, approvedLimit: Money, orderItems: Seq[PurchaseOrderLineItem] = Seq.empty) =
    new PurchaseOrder(id, approvedLimit, orderItems)

  implicit class PurchaserOrderEitherOps(either: Either[String, PurchaseOrder]) {

    /**
     * [[PurchaseOrder.addPurchaseItem]] を自然にメソッドチェーンするためのメソッド。
     *
     * @param item     商品
     * @param quantity 量
     */
    def addPurchaseItem(item: Part, quantity: Int): Either[String, PurchaseOrder] =
      either.flatMap(_.addPurchaseItem(item, quantity))
  }
}
