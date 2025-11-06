package com.cdlzrn.kwave.presentation.utils.outputTransformation

import androidx.compose.foundation.text.input.OutputTransformation

fun passwordOutputTransformation(): OutputTransformation {
    return OutputTransformation {
        this.replace(0, length, "•".repeat(length))
    }
}