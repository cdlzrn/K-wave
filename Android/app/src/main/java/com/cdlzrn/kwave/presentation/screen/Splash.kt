package com.cdlzrn.kwave.presentation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.GochiHand
import com.cdlzrn.kwave.presentation.theme.ui.Splash
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Splash(
    modifier: Modifier = Modifier,
    navToNext: () -> Unit,
    navToHome: () -> Unit,
) {
    var isAnimationFinished by remember { mutableStateOf(false) }
    var currentStep by remember { mutableIntStateOf(1) }
    val imageAlpha by animateFloatAsState(
        targetValue = if (currentStep >= 2) 1f else 0f,
        animationSpec = tween (durationMillis = 1000)
    )

    LaunchedEffect(key1 = currentStep) {
        when (currentStep) {
            1 -> { // Purple
                delay(500)
                currentStep = 2
            }
            2 -> { // Gradient
                delay(1000)
                currentStep = 3
            }
            3 -> { // Main name
                delay(1000)
                currentStep = 4
            }
            4 -> { // Sub text
                delay(2000)
                isAnimationFinished = true
            }
        }
    }

    LaunchedEffect(isAnimationFinished, authUserIdLong) {

        if (isAnimationFinished)
            if (authUserIdLong != -1L) navToHome()
            else navToNext()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Splash)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.gradient),
                    contentScale = ContentScale.FillBounds,
                    alpha = imageAlpha
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                AnimatedVisibility(
                    visible = currentStep >= 3,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000))
                ) {
                    Text(
                        text = "K-wave",
                        color = Color.White,
                        fontFamily = GochiHand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 64.sp
                    )
                }

                AnimatedVisibility(
                    visible = currentStep >= 4,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) + expandVertically()
                ) {
                    Text(
                        text = "k-pop next to you",
                        color = Color.White,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}