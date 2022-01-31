package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.Entity
import com.github.rinotc.purchaseSample.models.money.Money
import com.github.rinotc.purchaseSample.models.part.Part

final class PurchaseOrderLineItem(
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
