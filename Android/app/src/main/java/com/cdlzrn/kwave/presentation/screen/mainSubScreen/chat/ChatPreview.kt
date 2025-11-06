package com.cdlzrn.kwave.presentation.screen.mainSubScreen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ChatPreviewItem
import com.cdlzrn.kwave.data.model.ChatType
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray42


//TODO: Со временем разобраться
@Composable
fun ChatPreview(
    modifier: Modifier = Modifier,
    data: ChatPreviewItem
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        ){
            Image(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(data.image),
                contentDescription = data.title,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ){
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = data.title,
                        color = Gray100,
                        fontSize = 16.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = data.lastMessage,
                        color = Gray100,
                        fontSize = 12.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.Light,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column (
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                ){
                    Text(
                        text = data.time,
                        color = Gray100,
                        fontSize = 12.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.Light
                    )

                    if (data.unreadCount > 0) {
                        NotificationsCount(
                            modifier = Modifier.padding(top = 11.dp),
                            count = data.unreadCount
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 44.dp),
            thickness = 1.dp,
            color = Gray42
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column {
        ChatPreview(
            modifier = Modifier.padding(bottom = 8.dp),
            data = ChatPreviewItem(
                id = 0,
                image = R.drawable.avatar_0,
                title = "Тася",
                lastMessage = "Жду твоего ответа!",
                time = "19:48",
                unreadCount = 1,
                type = ChatType.DIALOG,
            )
        )
        ChatPreview(
            data = ChatPreviewItem(
                id = 0,
                image = R.drawable.avatar_0,
                title = "Тася",
                lastMessage = "Привет как дела это очень очень очень большая строка для проверки сокращения аээ орешки бих бох",
                time = "19:48",
                unreadCount = 0,
                type = ChatType.DIALOG,
            )
        )

    }
}