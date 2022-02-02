package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.EntityId

/**
 * 購入注文ID
 */
final class PurchaseOrderId private (val value: Long) extends EntityId[Long] {
  require(value >= 1, s"PurchaseOrderId's value must greater than equal 1, but $value")

  override def equals(other: Any): Boolean = other match {
    case that: PurchaseOrderId => value == that.value
    case _                     => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"PurchaseOrderId($value)"
}

object PurchaseOrderId {
  def apply(value: Long) = new PurchaseOrderId(value)
}
