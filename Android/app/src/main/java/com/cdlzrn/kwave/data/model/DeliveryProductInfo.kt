package com.cdlzrn.kwave.data.model

import com.cdlzrn.kwave.data.enums.Currency

data class DeliveryProductInfo(
    val id: Long,
    val name: String,
    val imageResID: Int,
    val productPrice: Int,
    val deliveryPrice: Int,
    val currency: Currency,
    val dayForDelivery: Int
)