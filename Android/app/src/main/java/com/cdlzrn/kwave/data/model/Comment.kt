package com.cdlzrn.kwave.data.model

data class Comment(
    val id: Long,
    val user: UserRegistrationData,
    val rate: Int,
    val text: String,
    val images: List<Int>
)