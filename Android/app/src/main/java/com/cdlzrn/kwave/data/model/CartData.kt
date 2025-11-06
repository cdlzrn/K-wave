package com.cdlzrn.kwave.data.model

data class CartData (
    val userId: Long,
    val productId: Long,
    val productCount: Int,
    val isSelected: Boolean
)