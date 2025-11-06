package com.cdlzrn.kwave.presentation.asset

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ShopCardItem
import com.cdlzrn.kwave.data.model.SliderConfig
import com.cdlzrn.kwave.data.model.SliderItem
import com.cdlzrn.kwave.presentation.asset.card.ShopCard
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy

@Composable
fun <T : SliderItem> RowSlider(
    modifier: Modifier = Modifier,
    config: SliderConfig<T>,
    contentPadding: Dp = 12.dp,
    cardContent: @Composable (index: Int, data: T) -> Unit,
) {
    Column(
        modifier = modifier
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = config.label,
                style = getGradientTextStyle(),
                fontSize = 16.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
            if (config.isHaveArrow){
                Image(
                    modifier = Modifier
                        .clickable{
                            config.onClickToArrow()
                                  },
                    painter = painterResource(R.drawable.arrow_gradient),
                    contentDescription = null,
                )
            }
        }

        LazyRow(
            contentPadding = PaddingValues(start = 16.dp)
        ){
            itemsIndexed(items = config.items){ index, item ->
                Box(
                    modifier = Modifier
                        .padding( end = contentPadding )
                ) {
                    cardContent(index, item)
                }
            }
        }
    }
}

@Composable
fun <T : SliderItem> RowSlider(
    modifier: Modifier = Modifier,
    config: SliderConfig<T>,
    contentPadding: Dp = 12.dp,
    cardContent: @Composable (data: T) -> Unit,
) {
    RowSlider(
        modifier = modifier,
        config = config,
        contentPadding = contentPadding,
        cardContent = { _, data -> cardContent(data) }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RowSlider(
        config = SliderConfig(
            label = "ABOBA",
            isHaveArrow = true,
            items = listOf(
                ShopCardItem(),
                ShopCardItem(),
                ShopCardItem(),
            )
        )
    ){ item ->
        ShopCard(
            data = item,
            inCart = false,
            onBuyClick = {d, f -> true}
        )
    }
}