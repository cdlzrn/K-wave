package com.cdlzrn.kwave.presentation.asset.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.enums.Currency
import com.cdlzrn.kwave.data.model.ShopCardItem
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100

@Composable
fun ShopCard(
    modifier: Modifier = Modifier,
    inCart: Boolean,
    onCardClick: () -> Unit = {},
    onBuyClick: (Long, Boolean) -> Boolean,
    data: ShopCardItem
) {

    var buttonFill by remember { mutableStateOf(!inCart) }
    var buttonText by remember(buttonFill) {
        mutableStateOf(
            if (buttonFill) "В корзину"
            else "В корзине"
        )
    }

    Column(
        modifier = modifier
            .width(130.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        Column(
            modifier = Modifier
                .clickable{
                    onCardClick()
                }
        ){
            Box(
                contentAlignment = Alignment.TopEnd
            ){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(131.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    painter = painterResource(data.imageResId),
                    contentDescription = data.name,
                    contentScale = ContentScale.FillWidth
                )
                Image(
                    modifier = Modifier
                        .padding(10.dp),
                    painter = painterResource(R.drawable.bookmark),
                    contentDescription = "Bookmark"
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = data.name,
                color = Gray100,
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Light
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = data.price.toString() + data.currency.symbol,
                color = Gray100,
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Medium
            )
        }
        GradientButton(
            modifier = Modifier
                .padding(top = 10.dp)
                .height(24.dp),
            fill = buttonFill,
            text = buttonText,
            fontWeight = FontWeight.Medium,
            fontSize = 12,
            roundSize = 8.dp,
            onClick = {
                if (onBuyClick(data.id, buttonFill))
                    buttonFill = !buttonFill
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ShopCard(
        onCardClick = {},
        data = ShopCardItem(
        1, 9999, "Abobus", Currency.RUB, R.drawable.ic_launcher_background,
        ),
        inCart = true,
        onBuyClick = {d, b -> true}
    )
}