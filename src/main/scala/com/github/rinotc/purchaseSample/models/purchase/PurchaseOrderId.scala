package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.EntityId

/**
 * 購入注文ID
 */
case class PurchaseOrderId(value: Long) extends EntityId[Long]
