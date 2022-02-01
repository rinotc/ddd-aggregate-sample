package com.github.rinotc.purchaseSample.models.purchase

import support.UnitTest

class PurchaseOrderLineItemIdTest extends UnitTest {
  "PurchaseOrderLineItemIdTest" should {
    "invariant" should {
      "IDの値は1以上" in {
        assertThrows[IllegalArgumentException] { PurchaseOrderLineItemId(0) }
      }
    }

    "equals" in {
      // given(前提条件): 内部の値の同じ異なるインスタンスを用意する
      // when(操作): 比較する
      // then(期待する結果): 同値と判定する
      val id11 = PurchaseOrderLineItemId(1)
      val id12 = PurchaseOrderLineItemId(1)
      id11 mustBe id12
    }
  }
}
