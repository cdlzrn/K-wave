package com.cdlzrn.kwave.data.model

import com.cdlzrn.kwave.data.enums.Currency

data class ProductInCartData(
    val id: Long,
    val image: Int,
    val price: Int,
    val name: String,
    val currency: Currency,
    val deliveryPrice: Int,
    val dayBeforeDelivery: Int,
    val countInCart: Int,
    val isSelected: Boolean,
)