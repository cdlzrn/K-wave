package com.cdlzrn.kwave.data.model

data class InterestItem(
    val id: Long,
    val name: String,
    val imageResId: Int,
    var isSelected: Boolean = false
)