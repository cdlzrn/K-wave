package com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.cartSubScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray70

@Composable
fun PaymentSuccess(
    modifier: Modifier = Modifier,
    navToShopHome: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Header()

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Корзина",
            color = Gray100,
            fontSize = 20.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start
        )
        Image(
            modifier = Modifier
                .size(width = 223.dp, height = 192.dp)
                .padding(top = 54.dp),
            painter = painterResource(R.drawable.full_cart),
            contentDescription = "Full cart"
        )
        Image(
            modifier = Modifier
                .padding(top = 25.dp)
                .size(24.dp)
            ,
            painter = painterResource(R.drawable.circle_checkbox_on),
            contentDescription = "Done"
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "Оплата прошла успешно!\nСпасибо за покупку!",
            color = Gray70,
            fontSize = 16.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        GradientButton(
            modifier = Modifier.padding(top = 18.dp),
            fill = false,
            text = "Готово",
            roundSize = 10.dp,
            onClick = {
                navToShopHome()
            }
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 20.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(R.drawable.menu),
            contentDescription = "Menu"
        )
        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.shop),
            contentDescription = "Shop"
        )
        Box(
            contentAlignment = Alignment.TopEnd
        ){
            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.cart),
                contentDescription = "Cart"
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PaymentSuccess(
        navToShopHome = {}
    )
}