package com.cdlzrn.kwave.data.mapper

import com.cdlzrn.kwave.data.entity.CartEntity
import com.cdlzrn.kwave.data.model.CartData

fun toCartEntity(data: CartData): CartEntity{
    return CartEntity(
        userId = data.userId,
        productId = data.productId,
        productCount = data.productCount,
        isSelected = data.isSelected
    )
}