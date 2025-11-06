package com.cdlzrn.kwave.data.model

import androidx.compose.ui.graphics.painter.Painter
import com.cdlzrn.kwave.presentation.screen.ScreensRoutes
import kotlin.reflect.KClass

class BottomNavBarItem(
    val label: String,
    val route: ScreensRoutes,
    val selectedRoutes: List<KClass<out ScreensRoutes>>,
    val imageSelected: Painter,
    val imageUnselected: Painter,
)