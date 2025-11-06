package com.cdlzrn.kwave.presentation.asset

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.data.model.BottomNavBarItem
import com.cdlzrn.kwave.presentation.screen.Chat
import com.cdlzrn.kwave.presentation.screen.Profile
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray70

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    setShowOops: (Boolean) -> Unit,
    items: List<BottomNavBarItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    NavigationBar (
        modifier = modifier
            .fillMaxWidth()
            .shadow(50.dp, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color.White),
        containerColor = Color.White,
        contentColor = Color.White,
        tonalElevation = 7.dp
    ){
        items.forEach { item ->
            val isSelected = currentRoute?.let { route ->
                item.selectedRoutes.any { routeClass ->

                    val qualifiedName = routeClass.qualifiedName

                    if (qualifiedName != null) {
                        route.startsWith(qualifiedName)
                    } else false
                }
            } ?: false

            NavigationBarItem(
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Gray70,
                    selectedIndicatorColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Gray70,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White,
                ),
                icon = {
                    if (isSelected) Image(item.imageSelected, contentDescription = item.label)
                    else Image(item.imageUnselected, contentDescription = item.label)
                },
                label = {
                    Text(
                        text = item.label,
                        style = TextStyle(
                            brush = if (isSelected) getGradientBrush()
                                    else SolidColor(Gray70)
                        ),
                        fontSize = 12.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.Medium
                    )
                },
                selected = isSelected,
                onClick = {
                    if (authUserIdLong != -1L || item.route !in listOf(Chat, Profile)) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    else setShowOops(true)
                }
            )
        }
    }
}