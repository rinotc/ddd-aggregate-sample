package com.github.rinotc.purchaseSample.models.purchase

import support.UnitTest

class PurchaseOrderIdTest extends UnitTest {
  "PurchaseOrderIdTest" should {
    "requirement" should {
      "PurchaseOrderIdのvalueは1以上" should {
        "valueが0の時例外を投げる" in {
          assertThrows[IllegalArgumentException] { PurchaseOrderId(0L) }
        }
        "valueが1の時、正常にインスタンスが生成される" in {
          PurchaseOrderId(1L).value mustBe 1L
        }
      }
    }

    "equals" should {
      "valueの値が同じならば、インスタンスが異なっても同値と判定する" in {
        val idA = PurchaseOrderId(1)
        val idB = PurchaseOrderId(1)

        idA mustBe idB
      }
    }
  }
}
