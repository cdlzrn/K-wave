package com.cdlzrn.kwave.presentation.utils

fun GetDeclensionOfAWord(
    num: Int,
    one: String,
    two: String,
    five: String
): String{
    val lastNum = num % 10

    if (num in 11..14) return five

    return when(lastNum){
        1 -> one
        in 2..4 -> two
        else -> five
    }
}
