package com.cdlzrn.kwave.data.model

import com.cdlzrn.kwave.data.enums.Currency

data class ProductData (
    val id: Long,
    val image: Int,
    val price: Int,
    val name: String,
    val sellerId: Long,
    val currency: Currency,
    val description: String,
)