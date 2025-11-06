package com.cdlzrn.kwave.presentation.utils.inputTransformation

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert

fun phoneInputTransformation(): InputTransformation {
    val goodList = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')
    return InputTransformation {
        if (length == 1) insert(0, "+7")
        if (length == 2) delete(0, 2)
        if (length > 11) delete(12, length)

        if (length > 1) {
            var ans = this.toString().substring(1, length)
            ans = ans.filter { it in goodList }
            delete(1, length)
            insert(1, ans)
        }
        if (length == 2) delete(0, 2)
    }
}
