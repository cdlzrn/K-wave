package com.cdlzrn.kwave.presentation.screen.mainSubScreen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ChatPreviewItem
import com.cdlzrn.kwave.data.model.ChatType
import com.cdlzrn.kwave.presentation.asset.getGradientBrush
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.GochiHandCyrillic
import com.cdlzrn.kwave.presentation.theme.ui.Gray55
import kotlinx.coroutines.launch

private val tabItems = listOf(
    ChatType.DIALOG,
    ChatType.GROUP
)

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {

    val dialogItems: List<ChatPreviewItem> = listOf(
        ChatPreviewItem(id = 0, image = R.drawable.avatar_0, title = "Тася",
            lastMessage = "Жду твоего ответа!", time = "19:48", unreadCount = 1, type = ChatType.DIALOG),
        ChatPreviewItem(id = 1, image = R.drawable.avatar_1, title = "Продавец",
            lastMessage = "Спасибо за отзыв!", time = "17:34", unreadCount = 0, type = ChatType.DIALOG),
        ChatPreviewItem(id = 2, image = R.drawable.avatar_2, title = "Женя",
            lastMessage = "Я: напиши, когда освободишься...", time = "17:07", unreadCount = 0, type = ChatType.DIALOG),
        ChatPreviewItem(id = 3, image = R.drawable.avatar_3, title = "Yuna",
            lastMessage = "Спокойной ночи~", time = "Вчера", unreadCount = 0, type = ChatType.DIALOG)
    )
    val groupItems: List<ChatPreviewItem> = listOf(
        ChatPreviewItem(id = 4, image = R.drawable.group_0, title = "BTS forever",
            lastMessage = "Tim: как дела у вас, ребят?", time = "14:33", unreadCount = 15, type = ChatType.GROUP),
        ChatPreviewItem(id = 5, image = R.drawable.group_1, title = "Stray Kids: new album",
            lastMessage = "Я: вы уже видели dance практику? Хочу выучить партию Чанбина", time = "19:48", unreadCount = 0, type = ChatType.GROUP)
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabItems.size }
    )
    val coroutineScope = rememberCoroutineScope()

    val currentContent = tabItems[pagerState.currentPage]

    Column(
        modifier = modifier
    ){
        Header()

        SwitchButtons(
            modifier = Modifier.padding(bottom = 31.dp),
            selectedChat = currentContent,
            changeToDialog =  {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(tabItems.indexOf(ChatType.DIALOG))
                }
            },
            changeToGroup = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(tabItems.indexOf(ChatType.GROUP))
                }
            },
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->

            val chatType = tabItems[pageIndex]
            val items = if (chatType == ChatType.DIALOG) dialogItems else groupItems

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxSize()
            ){
                items( items ){ item ->
                    ChatPreview(
                        modifier = Modifier.padding(bottom = 8.dp),
                        data = item
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 20.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(modifier = Modifier.size(36.dp))

        Text(
            text = "Чаты",
            style = getGradientTextStyle(),
            fontSize = 36.sp,
            fontFamily = GochiHandCyrillic,
            fontWeight = FontWeight.Light
        )

        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.add_person),
            contentDescription = "Add person"
        )
    }
}

@Composable
fun SwitchButtons(
    modifier: Modifier = Modifier,
    selectedChat: ChatType,
    changeToDialog: () -> Unit,
    changeToGroup: () -> Unit
) {
    val selected = TextStyle(
        brush = getGradientBrush(),
        fontSize = 20.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.SemiBold
    )

    val unselected = TextStyle(
        color = Gray55,
        fontSize = 20.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium
    )

    Row(
        modifier = modifier.padding(start = 16.dp)
    ){
        Column(
            modifier = Modifier.clickable{ changeToDialog() }
        ){
            Text(
                text = "Чаты",
                style = if (selectedChat == ChatType.DIALOG) selected else unselected,
            )
            HorizontalLine(
                modifier = Modifier.width(64.dp),
                isGradient = selectedChat == ChatType.DIALOG
            )
        }
        Column(
            modifier = Modifier.clickable{ changeToGroup() }
        ){
            Text(
                modifier = Modifier.padding(start = 33.dp),
                text = "Обсуждения",
                style = if (selectedChat == ChatType.GROUP) selected else unselected,
            )
            HorizontalLine(
                modifier = Modifier.width(164.dp),
                isGradient = selectedChat == ChatType.GROUP
            )
        }
    }
}

@Composable
private fun HorizontalLine(
    modifier: Modifier = Modifier,
    isGradient: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .then(
                if (isGradient) Modifier.background(brush = getGradientBrush())
                else Modifier.background(color = Gray55)
            )
    )
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    ChatScreen()
}