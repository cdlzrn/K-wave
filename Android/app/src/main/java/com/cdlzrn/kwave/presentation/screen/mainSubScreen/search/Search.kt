package com.cdlzrn.kwave.presentation.screen.mainSubScreen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray55

//TODO: Не убирается курсор после расфокуса
@Composable
fun Search(modifier: Modifier = Modifier) {
    val state = remember { TextFieldState("") }

    val fontSize = 16.sp
    val textColor = Gray55
    val cursorColor = textColor

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth(),
        state = state,
        textStyle = TextStyle(
            fontSize = fontSize,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(cursorColor),
        decorator = { innerTextField ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .paint(
                        painter = painterResource(id = R.drawable.gradient),
                        contentScale = ContentScale.FillBounds
                    )
                    .padding(1.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Image(
                    modifier = Modifier
                        .size(36.dp),
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search icon",
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    if (state.text.isEmpty())
                        Text(
                            text = "Поиск",
                            style = getGradientTextStyle(),
                            fontSize = fontSize,
                            fontFamily = Gilroy,
                            fontWeight = FontWeight.SemiBold,
                        )
                    innerTextField()
                }
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    Search()
}