package com.cdlzrn.kwave.data.model

data class UserRegistrationData(
    val name: String,
    val dateOfBirth: String,
    val mail: String,
    val phone: String,
    val password: String = "",
)