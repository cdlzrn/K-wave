package com.cdlzrn.kwave.data.model

data class SliderConfig <T : SliderItem>(
    val label: String,
    val isHaveArrow: Boolean = false,
    val items: List<T>,
    val onClickToArrow: () -> Unit = {},
)