package com.cdlzrn.kwave.data.converter

import androidx.room.TypeConverter
import com.cdlzrn.kwave.data.enums.Currency

open class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency?): String? = currency?.name

    @TypeConverter
    fun toCurrency(value: String?): Currency?{
        return value?.let { Currency.valueOf(it) }
    }
}