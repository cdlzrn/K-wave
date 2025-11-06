package com.cdlzrn.kwave.presentation.asset

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    fill: Boolean,
    text: String,
    fontFamily: FontFamily = Gilroy,
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontSize: Int = 16,
    roundSize: Dp = 30.dp,
    height: Dp = 44.dp,
    onClick: () -> Unit,
    isHaveArrow: Boolean = false
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(roundSize))
            .clickable(onClick = onClick)
            .background(getGradientBrush())
            .then(
                if (!fill) {
                    Modifier
                        .padding(1.dp)
                        .clip(RoundedCornerShape(roundSize))
                        .background(Color.White)
                } else {
                    Modifier
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = text,
                fontFamily = fontFamily,
                fontWeight = fontWeight,
                fontSize = fontSize.sp,
                style =
                    if (fill)
                        TextStyle(color = Color.White)
                    else
                        getGradientTextStyle()
            )
            if (isHaveArrow){
                Image(
                    modifier = Modifier.padding(start = 6.dp),
                    painter = painterResource(R.drawable.mini_arrow_gradient),
                    contentDescription = "Arrow"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column{
        GradientButton(
            modifier = Modifier.padding(bottom = 10.dp),
            fill = true,
            text = "Создать аккаунт",
            onClick = {}
        )
        GradientButton(
            modifier = Modifier.padding(bottom = 10.dp),
            fill = false,
            text = "Вход",
            onClick = {}
        )
        GradientButton(
            modifier = Modifier.padding(bottom = 10.dp),
            fill = false,
            text = "Вход",
            isHaveArrow = true,
            onClick = {}
        )

    }
}
