package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.money.Money
import com.github.rinotc.purchaseSample.models.{Aggregate, Entity}

/**
 * 購入注文
 *
 * @param id 購入注文ID
 * @param approvedLimit 承認限度額
 * @param orderItems 注文品目のリスト
 */
final class PurchaseOrder(
    val id: PurchaseOrderId,
    val approvedLimit: Money,
    val orderItems: Seq[PurchaseOrderLineItem]
) extends Entity[PurchaseOrderId]
    with Aggregate {

  require(mustLessEqualThanApprovedLimit, "注文総額は承認限度額以下でなくではならない") // クラス不変表明

  // この注文の通貨単位
  private val thisOrderCurrency = approvedLimit.currency

  /** 注文総額 */
  def total: Money = orderItems.map(_.totalAmount).foldLeft(Money(0, thisOrderCurrency)) { (m1, m2) => m1 plus m2 }

  override def canEqual(other: Any): Boolean = other.isInstanceOf[PurchaseOrder]

  // 注文総額は承認限度額以下でなくではならない
  private def mustLessEqualThanApprovedLimit: Boolean = total <= approvedLimit

  override def toString =
    s"PurchaseOrder(thisOrderCurrency=$thisOrderCurrency, id=$id, approvedLimit=$approvedLimit, orderItems=$orderItems, total=$total)"
}
