package com.cdlzrn.kwave.presentation.asset.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy

@Composable
fun ArtistCard(
    modifier: Modifier = Modifier,
    data: ArtistCardItem
) {
    Column(
        modifier = modifier
            .width(144.dp)
            .height(196.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.83f),
            painter = painterResource(data.imageResId),
            contentDescription = data.name,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = data.name,
                style = getGradientTextStyle(),
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview()
@Composable
private fun Preview() {
    ArtistCard( data = ArtistCardItem(imageResId = R.drawable.female_1) )
}