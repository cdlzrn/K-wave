package com.cdlzrn.kwave.presentation.asset

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cdlzrn.kwave.R


@Composable
fun ServiceRegisterButton(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .shadow(
                elevation = 2.dp,
                shape = CircleShape,

                )
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = imageModifier
                .size(30.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGoogleButton(){
    ServiceRegisterButton(
        painter = painterResource(R.drawable.google_logo),
        contentDescription = "Auth by Google",
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewAppleButton(){
    ServiceRegisterButton (
        painter = painterResource(R.drawable.apple),
        contentDescription = "Auth by Apple",
        imageModifier = Modifier
            .offset(y = (-2).dp),
        onClick = {}
    )
}