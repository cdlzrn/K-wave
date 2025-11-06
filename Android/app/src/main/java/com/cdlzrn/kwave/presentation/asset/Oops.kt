package com.cdlzrn.kwave.presentation.asset

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100

@Composable
fun Oops(
    modifier: Modifier = Modifier,
    navToRegister: () -> Unit,
    clickOnClose: () -> Unit,
    navToLogin: () -> Unit,
) {
    Column (
        modifier = modifier
            .fillMaxWidth(.6f)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(12.dp),
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(start = 20.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            modifier = Modifier
                .align(Alignment.End)
                .clickable{
                    clickOnClose()
                }
            ,
            painter = painterResource(R.drawable.close_gray),
            contentDescription = "Close"
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = "Упс! \nВам нужно войти, \nчтобы продолжить.",
            textAlign = TextAlign.Center,
            color = Gray100,
            fontSize = 14.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold
        )
        GradientButton(
            modifier = Modifier.padding(bottom = 4.dp),
            fill = true,
            text = "Создать аккаунт",
            fontWeight = FontWeight.Medium,
            fontSize = 15,
            roundSize = 10.dp,
            height = 30.dp,
            onClick = {
                navToRegister()
            }
        )
        GradientButton(
            fill = false,
            text = "Войти",
            fontWeight = FontWeight.Medium,
            fontSize = 15,
            roundSize = 10.dp,
            height = 30.dp,
            onClick = {
                navToLogin()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Oops(
            navToRegister = {},
            navToLogin = {},
            clickOnClose = {}
        )
    }
}