package com.cdlzrn.kwave.presentation.asset.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray25
import com.cdlzrn.kwave.presentation.theme.ui.Gray55
import com.cdlzrn.kwave.presentation.theme.ui.Gray8

@Composable
fun ArtistCircleCard(
    modifier: Modifier = Modifier,
    data: ArtistCardItem = ArtistCardItem(),
) {
    val isHavePlus = data.name == "plus"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(100.dp)
                .then(
                    if (data.imageResId == -1) {
                        Modifier
                            .drawBehind {
                                val stroke = Stroke(
                                    width = 3f,
                                    pathEffect = PathEffect.dashPathEffect(
                                        intervals = floatArrayOf(17f, 20f),
                                        phase = 0f
                                    )
                                )

                                inset(
                                    horizontal = 3f / 2f,
                                    vertical = 3f / 2f
                                ) {
                                    drawRoundRect(
                                        color = Gray55,
                                        size = this.size,
                                        cornerRadius = CornerRadius(150f),
                                        style = stroke
                                    )
                                }
                            }
                    }
                    else {
                        Modifier
                            .border(
                                color = Gray25,
                                width = 1.dp,
                                shape = CircleShape
                            )
                    }
                )
                .clip(CircleShape)
                .background(Gray8)
                .clickable {
                    if (isHavePlus) {
                        println("Click")
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (data.imageResId != -1){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(data.imageResId),
                    contentDescription = data.name,
                    contentScale = ContentScale.Crop
                )
            }
            else if (isHavePlus) {
                Image(
                    painter = painterResource(R.drawable.plus_gradient),
                    contentDescription = "addNewArtist"
                )
            }
        }
        if (data.imageResId != -1){
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = data.name,
                color = Gray100,
                fontSize = 16.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Row {
        ArtistCircleCard(
            data = ArtistCardItem(
                name = "plus"
            )
        )

        ArtistCircleCard()

        ArtistCircleCard(
            data = ArtistCardItem(
                imageResId = R.drawable.interests_16,
                name = "PSY"
            ),
        )
    }
}