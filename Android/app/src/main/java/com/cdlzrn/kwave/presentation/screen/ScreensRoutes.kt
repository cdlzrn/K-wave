package com.cdlzrn.kwave.presentation.screen

import kotlinx.serialization.Serializable


@Serializable
sealed interface ScreensRoutes

@Serializable
object AuthActivities:ScreensRoutes
@Serializable
object Splash:ScreensRoutes
@Serializable
object CreateOrLogin:ScreensRoutes
@Serializable
object Login:ScreensRoutes
@Serializable
object Register:ScreensRoutes
@Serializable
object Interests:ScreensRoutes


@Serializable
object MainActivities:ScreensRoutes
@Serializable
object Home:ScreensRoutes
@Serializable
object Search:ScreensRoutes
@Serializable
object Chat:ScreensRoutes
@Serializable
object Profile:ScreensRoutes



@Serializable
object ShopActivities:ScreensRoutes
@Serializable
object ShopHome:ScreensRoutes

@Serializable
data class ShopProduct(
    val productId: Long
):ScreensRoutes

@Serializable
object CartActivities:ScreensRoutes
@Serializable
object ShopEmptyCart:ScreensRoutes
@Serializable
object ShopFillCart:ScreensRoutes
@Serializable
object ShopCartPayment:ScreensRoutes
@Serializable
object ShopCartThanks:ScreensRoutes
