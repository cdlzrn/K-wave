package com.cdlzrn.kwave.presentation.screen.mainSubScreen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.data.model.SliderConfig
import com.cdlzrn.kwave.presentation.asset.RowSlider
import com.cdlzrn.kwave.presentation.asset.card.ArtistCard
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.GochiHandCyrillic

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    val items1 = listOf(
        ArtistCardItem(id = 0, name = "Le sserafim", imageResId = R.drawable.female_0),
        ArtistCardItem(id = 1, name = "Blackpink", imageResId = R.drawable.female_1),
        ArtistCardItem(id = 2, name = "(G)I-DLE", imageResId = R.drawable.female_2),
        ArtistCardItem(id = 3, name = "Jessi", imageResId = R.drawable.female_3),
    )

    val items2 = listOf(
        ArtistCardItem(id = 0, name = "Stray Kids", imageResId = R.drawable.male_0),
        ArtistCardItem(id = 1, name = "ONE US", imageResId = R.drawable.male_1),
        ArtistCardItem(id = 2, name = "Seventeen", imageResId = R.drawable.male_2),
        ArtistCardItem(id = 3, name = "Eric Nam", imageResId = R.drawable.male_3),
    )

    val items3 = listOf(
        ArtistCardItem(id = 0, name = "DPR IAN", imageResId = R.drawable.recommendation_0),
        ArtistCardItem(id = 1, name = "BIBI", imageResId = R.drawable.recommendation_1),
        ArtistCardItem(id = 2, name = "Enhypen", imageResId = R.drawable.recommendation_2),
        ArtistCardItem(id = 3, name = "ITZY", imageResId = R.drawable.recommendation_3),
    )

    val sliderConfigs = listOf(
        SliderConfig(label = "Женские группы", items = items1, isHaveArrow = true),
        SliderConfig(label = "Мужские группы", items = items2, isHaveArrow = true),
        SliderConfig(label = "Рекомендации", items = items3, isHaveArrow = true),
    )

    Column(
        modifier = modifier
            .verticalScroll(scrollState),
    ){
        Header()

        Text(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 18.dp),
            text = "Узнай больше \nартистов",
            style = getGradientTextStyle(),
            fontSize = 32.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold
        )

        Search(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )

        sliderConfigs.forEach{ config ->
            RowSlider(
                modifier = Modifier
                    .padding(bottom = 18.dp),
                config = config
            ) { item ->
                ArtistCard(data = item)
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 20.dp, bottom = 28.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Поиск",
            style = getGradientTextStyle(),
            fontSize = 36.sp,
            fontFamily = GochiHandCyrillic,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SearchScreen()
}