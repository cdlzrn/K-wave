package com.cdlzrn.kwave.presentation.utils.validator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SelectableDates
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
object DateValidator: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year <= LocalDate.now().year
    }
}