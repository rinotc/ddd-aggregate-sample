package com.github.rinotc.purchaseSample.models.part

import com.github.rinotc.purchaseSample.models.EntityId

/**
 * 商品ID
 */
case class PartId(value: Long) extends EntityId[Long]
