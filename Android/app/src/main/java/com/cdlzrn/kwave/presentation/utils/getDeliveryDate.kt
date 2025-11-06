package com.cdlzrn.kwave.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getDeliveryDate(countDayDelivery: Int): String{
    val russianLocale = Locale("ru")
    val deliveryDate = LocalDate.now().plusDays(countDayDelivery.toLong())

    val monthName = deliveryDate.month.getDisplayName(TextStyle.FULL, russianLocale)
    val dayOfMonth = deliveryDate.dayOfMonth
    val monthNameInGenitive = when (monthName.lowercase(russianLocale)) {
        "январь" -> "января"
        "февраль" -> "февраля"
        "март" -> "марта"
        "апрель" -> "апреля"
        "май" -> "мая"
        "июнь" -> "июня"
        "июль" -> "июля"
        "август" -> "августа"
        "сентябрь" -> "сентября"
        "октябрь" -> "октября"
        "ноябрь" -> "ноября"
        "декабрь" -> "декабря"
        else -> monthName
    }

    return "$dayOfMonth $monthNameInGenitive"
}