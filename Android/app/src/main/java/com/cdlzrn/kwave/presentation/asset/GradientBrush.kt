package com.cdlzrn.kwave.presentation.asset

import android.graphics.Matrix
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import com.cdlzrn.kwave.R

@Composable
fun getGradientBrush(): ShaderBrush {
    val image = ImageBitmap.imageResource(id = R.drawable.gradient_png)

    return remember(key1 = image) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val shader = ImageShader(image)
                val scaleX = size.width / image.width
                val scaleY = size.height / image.height

                val matrix = Matrix()
                matrix.setScale(scaleX, scaleY)
                shader.setLocalMatrix(matrix)

                return shader
            }
        }
    }
}

@Composable
fun getGradientTextStyle(): TextStyle {
    return TextStyle(brush = getGradientBrush())
}