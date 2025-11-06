package com.cdlzrn.kwave.presentation.asset

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.cdlzrn.kwave.R

@Composable
fun CheckBoxCircle(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onClick: () -> Unit
) {
    Image(
        modifier = modifier
            .clickable{
                onClick()
            },
        painter =
            if (checked) painterResource(R.drawable.circle_checkbox_on)
            else painterResource(R.drawable.circle_checkbox_off),
        contentDescription = "Circle checkbox"
    )
}

@Preview
@Composable
private fun Preview() {
    var state by remember { mutableStateOf(true) }

    Row(){
        CheckBoxCircle(
            checked = state,
            onClick = {state = !state}
        )
        CheckBoxCircle(
            checked = !state,
            onClick = {state = !state}
        )
    }
}