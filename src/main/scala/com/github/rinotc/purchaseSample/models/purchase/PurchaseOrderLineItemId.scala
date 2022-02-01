package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.EntityId

/**
 * 購入注文品目のID
 *
 * @param value IDの値
 */
final class PurchaseOrderLineItemId(val value: Int) extends EntityId[Int] {

  require(value >= 1, "購入注文品目のIDの値は1以上")

  override def equals(other: Any): Boolean = other match {
    case that: PurchaseOrderLineItemId => value == that.value
    case _                             => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"PurchaseOrderLineItemId($value)"
}

object PurchaseOrderLineItemId {
  def apply(value: Int) = new PurchaseOrderLineItemId(value)
}
