package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.money.Money
import com.github.rinotc.purchaseSample.models.part.{Part, PartId}
import support.UnitTest

class PurchaseOrderLineItemTest extends UnitTest {

  import Money._

  "PurchaseOrderLineItemTest" should {
    "totalAmount" in {
      val item     = Part(PartId(1L), "ギター", Money(300, USD))
      val lineItem = PurchaseOrderLineItem(PurchaseOrderLineItemId(1), item, 3)
      lineItem.totalAmount mustBe Money(900, USD)
    }
  }
}
