package com.cdlzrn.kwave.presentation.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.presentation.theme.ui.Error
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray55

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    isNecessarily: Boolean = false,
    isError: Boolean = false,
    inputTransformation: InputTransformation = InputTransformation{},
    outputTransformation: OutputTransformation = OutputTransformation{},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    state: TextFieldState = remember { TextFieldState("") },
    placeHolder: String,
    )
{
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth(),
        state = state,
        keyboardOptions = keyboardOptions,
        outputTransformation = outputTransformation,
        lineLimits = TextFieldLineLimits.SingleLine,
        inputTransformation = inputTransformation,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold,
            color = Gray100
        ),
        cursorBrush = SolidColor(Gray100),
        decorator = { innerTextField ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(30))
                    .border(
                        color = 
                            if (!isError) Gray55
                            else          Error,
                        width =
                            if (!isError) 1.dp
                            else          3.dp,
                        shape = RoundedCornerShape(30)
                    )
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ){
                if (state.text.isEmpty())
                    Text(
                        text =
                            if (isNecessarily)
                                placeHolder.plus("*")
                            else
                                placeHolder,
                        fontSize = 14.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.SemiBold,
                        color = Gray55
                    )
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun PreviewNecessarily() {
    TextField(
        placeHolder = "Пароль",
        isNecessarily = true,
        isError = true
    )
}

@Preview
@Composable
fun PreviewNoNecessarily() {
    TextField(
        placeHolder = "E-mail",
        isNecessarily = false
    )
}