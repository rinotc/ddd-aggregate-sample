package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.Entity
import com.github.rinotc.purchaseSample.models.money.Money
import com.github.rinotc.purchaseSample.models.part.Part

/**
 * 購入注文品目
 *
 * 集約（[[PurchaseOrder]]）の内部エンティティなので、集約内部でIDは一意であれば良い。
 *
 * @param id       購入注文品目ID
 * @param part     商品
 * @param quantity 量
 */
final class PurchaseOrderLineItem private (
    val id: PurchaseOrderLineItemId,
    private val part: Part,
    private val quantity: Int
) extends Entity[PurchaseOrderLineItemId] {

  /**
   * 総額
   */
  def totalAmount: Money = part.price times quantity

  override def canEqual(other: Any): Boolean = other.isInstanceOf[PurchaseOrderLineItem]

  override def toString = s"PurchaseOrderLineItem($id, $part, $quantity)"
}

object PurchaseOrderLineItem {
  def apply(id: PurchaseOrderLineItemId, part: Part, quantity: Int) = new PurchaseOrderLineItem(id, part, quantity)
}
