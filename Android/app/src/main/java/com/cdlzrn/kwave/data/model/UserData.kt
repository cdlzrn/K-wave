package com.cdlzrn.kwave.data.model

import java.time.LocalDate

data class UserData(
    val id: Long,
    val name: String,
    val avatar: Int,
    val mail: String?,
    val phone: String,
    val nickName: String?,
    val rate: Float,
    val dateOfBirth: LocalDate,
)