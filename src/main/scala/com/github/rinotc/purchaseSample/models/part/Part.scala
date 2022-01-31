package com.github.rinotc.purchaseSample.models.part

import com.github.rinotc.purchaseSample.models.Entity
import com.github.rinotc.purchaseSample.models.money.Money

/**
 * 商品
 *
 * @param id 商品ID
 * @param name   商品名
 * @param price  価格
 */
final class Part(val id: PartId, val name: String, val price: Money) extends Entity[PartId] {

  override def canEqual(other: Any): Boolean = other.isInstanceOf[Part]

  override def equals(other: Any): Boolean = other match {
    case that: Part => id == that.id
    case _          => false
  }

  override def hashCode(): Int = id.##

  override def toString = s"Item(id = ${id.value}, name = $name, price = $price)"
}
