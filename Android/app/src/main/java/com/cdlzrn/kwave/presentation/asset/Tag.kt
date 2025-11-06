package com.cdlzrn.kwave.presentation.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .height(25.dp)
            .clip(RoundedCornerShape(10.dp))
            .wrapContentWidth()
            .background(getGradientBrush())
            .padding(1.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
        ,
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp),
            text = text,
            color = Gray100,
            fontSize = 14.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Column(){
        Tag(modifier = Modifier.padding(bottom = 10.dp), text = "BTS")
        Tag(modifier = Modifier.padding(bottom = 10.dp), text = "Art")
        Tag(modifier = Modifier.padding(bottom = 10.dp), text = "Something")
    }
}