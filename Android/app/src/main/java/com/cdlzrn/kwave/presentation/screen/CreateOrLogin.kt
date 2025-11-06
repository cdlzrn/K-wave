package com.cdlzrn.kwave.presentation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.GochiHand
import com.cdlzrn.kwave.presentation.theme.ui.Gradient
import com.cdlzrn.kwave.presentation.viewmodel.CreateOrLoginViewModel

// TODO: Анимация появления
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateOrLogin(
    modifier: Modifier = Modifier,
    navigateToCreate: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToIncognito: () -> Unit,
    viewModel: CreateOrLoginViewModel = viewModel<CreateOrLoginViewModel>()
) {
    viewModel.fillDataBase()

    Column (
        modifier = modifier
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = .65f)
                    .paint(
                        painter = painterResource(id = R.drawable.gradient_create_or_login),
                        contentScale = ContentScale.FillBounds
                    )
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ){
                Text(
                    text = "K-wave",
                    color = Color.White,
                    fontFamily = GochiHand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 64.sp
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                GradientButton(
                    fill = true,
                    text = "Создать аккаунт",
                    onClick = navigateToCreate
                )
                Spacer(modifier = Modifier.height(12.dp))
                GradientButton(
                    fill = false,
                    text = "Войти",
                    onClick = navigateToLogin
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier
                        .clickable(onClick = navigateToIncognito),
                    text = "Продолжить без входа",
                    color = Gradient,
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                )
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun PreviewScreen() {
//    CreateOrLogin(
//        navigateToCreate = {},
//        navigateToLogin = {},
//        navigateToIncognito = {}
//    )
//}


