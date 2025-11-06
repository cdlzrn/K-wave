package com.cdlzrn.kwave.presentation.asset

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cdlzrn.kwave.R

@Composable
fun CheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Box(
        modifier = modifier
    ){
        Image(
            painter = painterResource(id = R.drawable.checkbox_off_icon),
            contentDescription = "Unchecked",
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(34))
                .background(Color.White)
                .clickable { onCheckedChange() }
        )
        AnimatedVisibility(
            visible = checked,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.checkbox_on_icon),
                contentDescription = "Checked",
                modifier = Modifier
                    .size(12.dp)
                    .clickable { onCheckedChange() }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCheckBoxOff(){

    var checked by remember { mutableStateOf(false) }

    CheckBox(
        checked = checked,
        onCheckedChange = { checked = !checked}
    )
}

@Preview
@Composable
private fun PreviewCheckBoxOn(){
    var checked by remember { mutableStateOf(true) }

    CheckBox(
        checked = checked,
        onCheckedChange = { checked = !checked}
    )
}