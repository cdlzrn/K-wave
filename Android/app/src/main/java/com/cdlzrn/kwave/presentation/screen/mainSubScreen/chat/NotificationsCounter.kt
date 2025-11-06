package com.cdlzrn.kwave.presentation.screen.mainSubScreen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.presentation.asset.getGradientBrush
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy

@Composable
fun NotificationsCount(
    modifier: Modifier = Modifier,
    count: Int
) {
    Box(
        modifier = modifier
            .height(16.dp)
            .defaultMinSize(minWidth = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(brush = getGradientBrush()),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.padding(top = 3.dp, bottom = 2.dp, start = 3.dp, end = 3.dp),
            text = count.toString(),
            color = Color.White,
            fontSize = 10.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Row(){
        NotificationsCount(
            modifier = Modifier.padding(end = 5.dp),
            count = 9
        )
        NotificationsCount(
            modifier = Modifier.padding(end = 5.dp),
            count = 99
        )
        NotificationsCount(
            modifier = Modifier.padding(end = 5.dp),
            count = 999
        )
        NotificationsCount(
            count = 9999
        )
    }
}