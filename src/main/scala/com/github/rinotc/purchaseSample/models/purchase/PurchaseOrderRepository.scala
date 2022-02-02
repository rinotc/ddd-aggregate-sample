package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.Repository

trait PurchaseOrderRepository extends Repository[PurchaseOrder] {

  def findById(id: PurchaseOrderId): Option[PurchaseOrder]

  def insert(purchaseOrder: PurchaseOrder): Unit

  def update(purchaseOrder: PurchaseOrder): Either[String, Unit]

  def delete(id: PurchaseOrderId): Unit
}
