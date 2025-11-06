package com.cdlzrn.kwave.presentation.screen

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.viewmodel.InterestViewModel
import kotlin.math.ceil

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Interests(
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit,
    viewModel: InterestViewModel = viewModel<InterestViewModel>()
) {
    val initialInterests by viewModel.selectableArtists.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.gradient),
                contentScale = ContentScale.FillBounds
            )
    ){
        Text(
            modifier = Modifier
                .padding(
                    top = 135.dp,
                    bottom = 28.dp,
                    start = 16.dp
                ),
            textAlign = TextAlign.Start,
            text = "Ваши интересы",
            color = Color.White,
            fontSize = 32.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold
        )

        val offsetActor = 55.dp
        val paddingBetween = 10.dp

        val offsetActorPx = LocalDensity.current.run { offsetActor.toPx() }

        val listState = rememberLazyListState()

        val rowCount = 5
        val columnCount = ceil((initialInterests.size + rowCount - 1f) / rowCount).toInt()

        LaunchedEffect(key1 = Unit) {
            listState.scrollBy(offsetActorPx)
        }

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(-offsetActor + paddingBetween),
            state = listState,
        ) {
            items(columnCount) { columnIndex ->
                Column (
                    modifier = Modifier
                        .padding(end = offsetActor)
                ) {
                    for (i in 0..rowCount - 1){
                        val itemIndex = columnIndex * rowCount + i
                        if (itemIndex < initialInterests.size) {
                            val item = initialInterests[itemIndex]

                            Artist(
                                modifier = Modifier
                                    .offset(if (i % 2 == 0) offsetActor else 0.dp),
                                painter = painterResource(id = item.imageResId),
                                description = item.name,
                                isSelected = item.isSelected,
                                onClick = { viewModel.onInterestClick(item.id) }
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 66.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier
                    .padding( start = 30.dp )
                    .clickable{
                        navigateToMain()
                    },
                textAlign = TextAlign.Start,
                text = "Пропустить",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Light
            )
            Row(
                modifier = Modifier
                    .padding( end = 30.dp ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier
                        .clickable{
                            viewModel.saveInterests()
                            navigateToMain()
                        },
                    textAlign = TextAlign.Start,
                    text = "Далее",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.SemiBold,
                )
                Icon(
                    modifier = Modifier
                        .size(13.dp)
                        .clickable{
                            navigateToMain()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ico_arrow_right),
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun Artist(
    modifier: Modifier = Modifier,
    painter: Painter,
    description: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val blurRadius = 137f
    val blurHeight = 60f

    val paint = remember {
        Paint().asFrameworkPaint().apply {
            color = Color.White.toArgb()
            maskFilter = BlurMaskFilter(
                blurHeight,
                BlurMaskFilter.Blur.SOLID
            )
        }
    }

    Image(
        modifier = modifier
            .size(100.dp)
            .clickable{ onClick() }
            .then(
                if (isSelected)
                    Modifier
                        .drawBehind {
                            drawIntoCanvas { canvas ->
                                val centerX = size.width / 2
                                val centerY = size.height / 2

                                canvas.nativeCanvas.drawCircle(
                                    centerX,
                                    centerY,
                                    blurRadius,
                                    paint
                                )
                            }
                        }
                else Modifier
            )
            .clip(CircleShape)
            .background(Color.Black)
        ,
        painter = painter,
        contentDescription = description,
        contentScale = ContentScale.FillBounds
    )
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun PreviewInterests() {
//    Interests(
//        navigateToMain = {}
//    )
//}

@Preview(showBackground = true)
@Composable
private fun PreviewArtist() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ){
        Artist(
            painter = painterResource(id = R.drawable.logo),
            description = " ",
            isSelected = true,
            onClick = { }
        )
        Artist(
            painter = painterResource(id = R.drawable.logo),
            description = " ",
            isSelected = false,
            onClick = { }
        )
    }
}