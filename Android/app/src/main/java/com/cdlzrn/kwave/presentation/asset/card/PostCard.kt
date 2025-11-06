package com.cdlzrn.kwave.presentation.asset.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.PostData
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray70
import java.time.LocalDateTime

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    data: PostData,
) {

    var isTextHide by remember { mutableStateOf(true) }
    val contentSizeAnimate = tween<IntSize>(
        durationMillis = 200,
        easing = FastOutSlowInEasing
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .animateContentSize(animationSpec = contentSizeAnimate),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){
            Header(
                modifier = Modifier.padding(bottom = 14.dp),
                data = data
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .animateContentSize(animationSpec = contentSizeAnimate),
                text = data.description,
                color = Gray100,
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Light,
                overflow = TextOverflow.Ellipsis,
                maxLines =  if (isTextHide) 5
                            else Int.MAX_VALUE
            )
            Text(
                modifier = Modifier
                    .clickable {
                        isTextHide = !isTextHide
                    }
                    .padding(bottom = 14.dp),
                text = if (isTextHide) "Показать еще" else "Скрыть",
                style = getGradientTextStyle(),
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Light,
            )
            Image(
                modifier = Modifier
                    .height(444.dp)
                    .padding(bottom = 14.dp),
                painter = painterResource(data.image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Footer(
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                countLike = data.countLike.toString(),
                countRepost = data.countRepost.toString(),
                countComment = data.countComment.toString()
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    data: PostData
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ){
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(data.authorImage),
            contentDescription = data.authorName,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(start = 4.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = data.authorName,
                color = Gray100,
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Тут время которое меняется.. 100%",
                color = Gray70,
                fontSize = 10.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Light

            )
        }
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    countLike: String,
    countComment: String,
    countRepost: String
) {
    val textStyle = TextStyle(
        fontSize = 10.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.Light
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Image(
                modifier = Modifier.padding(end = 4.dp),
                painter = painterResource(R.drawable.like),
                contentDescription = "Like"
            )
            Text(
                modifier = Modifier.padding(end = 6.dp),
                text = countLike,
                style = textStyle
            )
            Image(
                modifier = Modifier.padding(end = 4.dp),
                painter = painterResource(R.drawable.comment),
                contentDescription = "Comment"
            )
            Text(
                modifier = Modifier.padding(end = 6.dp),
                text = countComment,
                style = textStyle
            )
            Image(
                modifier = Modifier.padding(end = 4.dp),
                painter = painterResource(R.drawable.shared),
                contentDescription = "Shared"
            )
            Text(
                text = countRepost,
                style = textStyle
            )
        }
        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.bookmark_mini),
            contentDescription = "Bookmark"
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PostCard(
        data = PostData(
            authorName = "BTS or 방탕소년단",
            authorImage = R.drawable.bts,
            time = LocalDateTime.of(2024, 12, 1, 6,0, 0),
            description = "Обновление Instagram Bazaar Korea:\n\nДжин в интервью -\n\nОн рисует букву \"V\" своими длинными пальцами и улыбается, как мальчик, но когда съемки начинаются всерьез, он возвращается к лицу красивого взрослого мужчины, как будто этого никогда раньше не случалось. Между игривостью и серьезностью. Когда-то я думал, что истинное сердце Джина находится где-то между этими двумя. Эта мысль была ошибочной. Оба были Джинами.\n\nГлавный герой сентябрьского номера Bazaar, наполненное душой интервью и фото Джина находятся в сентябрьском номере.",
            image = R.drawable.post_0
        )
    )
}