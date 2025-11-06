package com.cdlzrn.kwave.data.model

import com.cdlzrn.kwave.data.enums.Currency

data class ProductForShopData (
    val id: Long,
    val image: Int,
    val price: Int,
    val name: String,
    val sellerName: String,
    val currency: Currency,
)