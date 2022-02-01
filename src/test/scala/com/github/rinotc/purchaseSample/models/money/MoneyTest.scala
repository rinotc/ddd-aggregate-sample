package com.github.rinotc.purchaseSample.models.money

import support.UnitTest

class MoneyTest extends UnitTest {
  "MoneyTest" should {

    import Money._

    "equals" should {
      "量と通貨単位が同値ならば、同値とみなす" in {
        (Money(100.01, EUR) == Money(100.01, USD)) mustBe false
        (Money(100.01, USD) == Money(100.01, EUR)) mustBe false
        (Money(100, JPY) == Money(100, JPY)) mustBe true
      }
    }
    "$less$eq" should {
      "通貨単位が異なるもの同士の比較はできない" in {
        assertThrows[IllegalArgumentException] {
          Money(100, JPY) <= Money(300.33, USD)
        }
      }

      "通貨単位が同じもの同士の比較" in {
        Money(300, JPY) must be <= Money(300, JPY)
        Money(100, JPY) must be <= Money(300, JPY)
      }
    }

    "$less" in {
      Money(100, JPY) must be < Money(300, JPY)
    }

    "$greater$eq" in {
      Money(300, JPY) must be >= Money(300, JPY)
      Money(301, JPY) must be >= Money(300, JPY)
    }

    "$greater" in {
      Money(300, JPY) must be > Money(100, JPY)
    }

    "plus" should {
      "同じ通貨ならば加算できる" in {
        Money(300, JPY).plus(Money(500, JPY)) mustBe Money(800, JPY)
      }

      "異なる通貨の場合は、加算できない" in {
        val usd = Money(500.0, USD)
        val yen = Money(300, JPY)
        assertThrows[IllegalArgumentException] {
          yen plus usd
        }
      }
    }

    "times" in {
      Money(333, JPY).times(3) mustBe Money(999, JPY)
    }
  }
}
