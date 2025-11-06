package com.cdlzrn.kwave.data.model

import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.enums.Currency

sealed interface SliderItem

data class ShopCardItem(
    val id: Long = 0L,
    val price: Int = 9999,
    val name: String = "Aboba",
    val currency: Currency = Currency.RUB,
    val imageResId: Int = R.drawable.ic_launcher_background,
): SliderItem

data class CartItem(
    val id: Long = 0L,
    val price: Int = 9999,
    val name: String = "Aboba",
    val currency: Currency = Currency.RUB,
    val imageResId: Int = R.drawable.ic_launcher_background,
    val countInCart: Int,
    val deliveryPrice: Int,
    val isSelected: Boolean,
): SliderItem

data class ArtistCardItem(
    val id: Long = 0,
    val name: String = "",
    val imageResId: Int = -1,
): SliderItem
