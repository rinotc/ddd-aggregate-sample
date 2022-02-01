package com.github.rinotc.purchaseSample.models.money

import com.github.rinotc.purchaseSample.models.ValueObject

import java.util.Currency

/**
 * 金額を示すクラス
 *
 * @see <a href="https://github.com/sisioh/baseunits-scala/blob/main/library/src/main/scala/org/sisioh/baseunits/scala/money/Money.scala">
 *      参考になる -> Money.scala
 *      </a>
 * @param amount   量
 * @param currency 通貨
 */
final class Money(val amount: BigDecimal, val currency: Currency) extends ValueObject with Ordered[Money] {

  require(
    amount.scale <= currency.getDefaultFractionDigits,
    s"Scale of amount does not match currency, amount scale: ${amount.scale}, currency: $currency, currency scale: ${currency.getDefaultFractionDigits}"
  )

  def plus(other: Money): Money = {
    require(sameCurrency(other))
    new Money(amount + other.amount, currency)
  }

  def times(factor: BigDecimal): Money = {
    new Money(amount * factor, currency)
  }

  override def equals(other: Any): Boolean = other match {
    case that: Money =>
      amount == that.amount &&
        currency == that.currency
    case _ => false
  }

  override def compare(that: Money): Int = {
    require(sameCurrency(that))
    amount.compare(that.amount)
  }

  override def hashCode(): Int = {
    val state = Seq(amount, currency)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"$amount $currency"

  private def sameCurrency(other: Money): Boolean = this.currency == other.currency

}

object Money {
  val JPY: Currency = Currency.getInstance("JPY")
  val USD: Currency = Currency.getInstance("USD")
  val EUR: Currency = Currency.getInstance("EUR")

  def apply(amount: BigDecimal, currency: Currency) = new Money(amount, currency)
}
