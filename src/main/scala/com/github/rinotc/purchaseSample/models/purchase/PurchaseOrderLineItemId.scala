package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.EntityId

final class PurchaseOrderLineItemId(val value: Int) extends EntityId[Int] {

  override def equals(other: Any): Boolean = other match {
    case that: PurchaseOrderLineItemId => value == that.value
    case _                             => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"PurchaseOrderLineItemId($value)"
}
