package com.github.rinotc.purchaseSample.models.purchase

import com.github.rinotc.purchaseSample.models.money.Money
import com.github.rinotc.purchaseSample.models.money.Money._
import com.github.rinotc.purchaseSample.models.part.{Part, PartId}
import support.UnitTest

class PurchaseOrderTest extends UnitTest {

  "PurchaseOrderTest" should {

    val guitar   = Part(PartId(1), "ギター", Money(100, USD))
    val trombone = Part(PartId(2), "トロンボーン", Money(200, USD))

    "invariant" should {
      "購入注文総額は承認限度額を超えてはならない" in {
        // given(前提条件):購入注文品目を準備する
        val lineItem1 = PurchaseOrderLineItem(PurchaseOrderLineItemId(1), guitar, 5)   // 500 USD
        val lineItem2 = PurchaseOrderLineItem(PurchaseOrderLineItemId(2), trombone, 5) // 1000 USD
        // when(操作): 承認限度額を1,000USD, 上記2つのアイテムでPurchaseOrderインスタンスを生成する
        // then(期待する結果): 例外を投げる
        assertThrows[IllegalArgumentException] {
          PurchaseOrder(PurchaseOrderId(1), approvedLimit = Money(1_000, USD), Seq(lineItem1, lineItem2))
        }
      }

      "購入注文品目（PurchaseOrderLineItem）のIDは集約内部で重複しない" in {
        // given(前提条件): 同じIDのPurchaseOrderLineItemを2つ用意する
        val lineItem1 = PurchaseOrderLineItem(PurchaseOrderLineItemId(1), guitar, 2)
        val lineItem2 = PurchaseOrderLineItem(PurchaseOrderLineItemId(1), trombone, 3)
        // when(操作): 同じIDの購入注文品目のインスタンスを含むリストで、PurchaserOrderインスタンスを生成する
        // then(期待する結果): 例外を投げる
        assertThrows[IllegalArgumentException] {
          PurchaseOrder(PurchaseOrderId(1), approvedLimit = Money(1_000, USD), Seq(lineItem1, lineItem2))
        }
      }
    }

    "total" in {
      // given(前提条件): 購入注文を準備する
      val orderGuitar   = PurchaseOrderLineItem(PurchaseOrderLineItemId(1), guitar, 3)
      val orderTrombone = PurchaseOrderLineItem(PurchaseOrderLineItemId(2), trombone, 2)
      val purchaseOrder =
        PurchaseOrder(id = PurchaseOrderId(1), approvedLimit = Money(1_000, USD), Seq(orderGuitar, orderTrombone))
      // when(操作):
      // then(期待する結果): 合計金額が期待値と一致する
      purchaseOrder.total mustBe Money(700, USD)
    }

    "addPurchaseItem" should {
      "新たに購入注文品目を追加した時、承認限度額を超えた場合にLeftを返す" in {
        // given(前提条件): アイテムが空の状態でPurchaseOrderインスタンスを生成する
        val purchaseOrder = PurchaseOrder(PurchaseOrderId(1), approvedLimit = Money(1_000, USD))
        // when(操作): 承認限度額を超えるようにアイテムを追加する
        val addResult = purchaseOrder
          .addPurchaseItem(guitar, 3)   // 300USD
          .addPurchaseItem(trombone, 4) // 800USD -> 1,100USD
        // then(期待する結果): Left を返す
        addResult.isLeft mustBe true
      }

      "新たに購入注文を追加した時、承認限度額を超えなければ、Rightでその要素も追加された新たなインスタンスを返す" in {
        // given(前提条件): アイテムが空の状態でPurchaseOrderインスタンスを生成する
        val purchaseOrder = PurchaseOrder(PurchaseOrderId(1), approvedLimit = Money(1_000, USD))
        // when(操作): 承認限度額を超えないようにアイテムを追加する
        val result = purchaseOrder
          .addPurchaseItem(guitar, 3)   // 300USD
          .addPurchaseItem(trombone, 2) // 400USD -> 700USD
          .toOption
          .get
        // then(期待する結果): 合計額が700USD
        result.total mustBe Money(700, USD)
      }
    }
  }
}
