package com.cdlzrn.kwave.presentation.asset.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray25

@Composable
fun ArtistRoundedCard(
    modifier: Modifier = Modifier,
    data: ArtistCardItem,
    onClickToArrow: () -> Unit = {}
) {
    val radius: Dp = 12.dp

    Row (
        modifier = modifier
            .width(283.dp)
            .clip(RoundedCornerShape(radius))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Gray25,
                shape = RoundedCornerShape(radius)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (data.imageResId != -1) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    painter = painterResource(data.imageResId),
                    contentDescription = data.name,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = data.name,
                color = Gray100,
                fontSize = 16.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Medium
            )
        }

        Image(
            modifier = Modifier
                .padding(end = 15.dp)
                .clickable {
                    onClickToArrow()
                },
            painter = painterResource(R.drawable.arrow_gradient),
            contentDescription = "Arrow"
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ArtistRoundedCard(
        data = ArtistCardItem(
            name = "BTS", imageResId = R.drawable.bts
        )
    )
}