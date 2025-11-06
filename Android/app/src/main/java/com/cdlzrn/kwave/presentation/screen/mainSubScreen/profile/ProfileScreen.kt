package com.cdlzrn.kwave.presentation.screen.mainSubScreen.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.Graph.deleteUserId
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.data.model.PostData
import com.cdlzrn.kwave.data.model.ShopCardItem
import com.cdlzrn.kwave.data.model.SliderConfig
import com.cdlzrn.kwave.data.model.UserData
import com.cdlzrn.kwave.presentation.asset.RowSlider
import com.cdlzrn.kwave.presentation.asset.SettingsItem
import com.cdlzrn.kwave.presentation.asset.card.ArtistCircleCard
import com.cdlzrn.kwave.presentation.asset.card.PostCard
import com.cdlzrn.kwave.presentation.asset.card.ShopCard
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.GochiHandCyrillic
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray25
import com.cdlzrn.kwave.presentation.theme.ui.Gray42
import com.cdlzrn.kwave.presentation.utils.GetDeclensionOfAWord
import com.cdlzrn.kwave.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navToProduct: (Long) -> Unit = {},
    navToCreateOrLogin: () -> Unit = {},
    setVisibleBottomBar: ( Boolean ) -> Unit = {},
    viewModel: ProfileViewModel = viewModel<ProfileViewModel>()
) {

    val artistsConfigState: SliderConfig<ArtistCardItem>? by viewModel.artistsConfigState.collectAsStateWithLifecycle()
    val artistsConfig = artistsConfigState

    val shopConfigState: SliderConfig<ShopCardItem>? by viewModel.shopConfigState.collectAsStateWithLifecycle()
    val shopConfig = shopConfigState

    val allPostState: List<PostData> by viewModel.allPostState.collectAsStateWithLifecycle()
    val allPost = allPostState

    val productIdInCartState: List<Long>? by viewModel.productIdInCartState.collectAsStateWithLifecycle()
    val productIdInCart = productIdInCartState

    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box{
        Box(
            modifier = Modifier.zIndex(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedVisibility(
                visible = isBottomSheetVisible,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray100.copy(alpha = 0.5f))
                        .pointerInput(Unit) {
                            detectTapGestures {
                                isBottomSheetVisible = false
                                setVisibleBottomBar(true)
                            }
                        }
                )
            }

            AnimatedVisibility(
                visible = isBottomSheetVisible,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                SettingsSheet(
                    onClose = {
                        scope.launch{
                            isBottomSheetVisible = false
                            deleteUserId()
                            navToCreateOrLogin()
                        }
                    }
                )
            }
        }
        LazyColumn(
            modifier = modifier,
        )
        {
            item {
                Header(
                    countPost = allPost.size,
                    countProduct = shopConfig?.items?.size ?: 0,
                    countFollowed = viewModel.followSize,
                    setVisibleBottomBar = setVisibleBottomBar,
                    setBottomSheetVisible = { isBottomSheetVisible = it },
                    viewModel = viewModel
                )

                if (artistsConfig != null) {
                    if (!artistsConfig.items.isEmpty()) {
                        RowSlider(
                            modifier = Modifier.padding(top = 24.dp, bottom = 19.dp),
                            contentPadding = 32.dp,
                            config = artistsConfig
                        ) { item ->
                            ArtistCircleCard(data = item)
                        }
                    }
                }

                if (shopConfig != null && productIdInCart != null) {
                    if (!shopConfig.items.isEmpty()) {
                        RowSlider(
                            modifier = Modifier
                                .padding(top = 24.dp),
                            config = shopConfig
                        ) { item ->
                            ShopCard(
                                data = item,
                                onBuyClick = { productId, isAdd ->
                                    if (isAdd) viewModel.addProductToCart(productId = productId)
                                    else viewModel.deleteProductFromCart(productId = productId)
                                    true
                                },
                                inCart = item.id in productIdInCart,
                                onCardClick = { navToProduct(item.id) }
                            )
                        }
                    }
                }

                if (!allPost.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 12.dp),
                        text = "Мои публикации",
                        style = getGradientTextStyle(),
                        fontSize = 16.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.SemiBold
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 18.dp),
                        thickness = 1.dp, color = Gray42
                    )
                }
            }

            items(allPost) { post ->
                PostCard(
                    modifier = Modifier.padding(bottom = 20.dp),
                    data = post
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    countPost: Int,
    countProduct: Int,
    countFollowed: Int,
    setBottomSheetVisible: (Boolean) -> Unit,
    setVisibleBottomBar: (Boolean) -> Unit,
    viewModel: ProfileViewModel,
) {
    val userState: UserData?    by viewModel.userState.collectAsStateWithLifecycle()
    val countFriendState: Int?   by viewModel.countFriendState.collectAsStateWithLifecycle()
    val user = userState
    val countFriend = countFriendState

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(495.dp)
            .background(Gray25),
        contentAlignment = Alignment.TopStart
    ) {
        if (user != null){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(user.avatar),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.qr),
                        contentDescription = "QR"
                    )
                    Text(
                        text = "Профиль",
                        style = getGradientTextStyle(),
                        fontSize = 36.sp,
                        fontFamily = GochiHandCyrillic,
                        fontWeight = FontWeight.Normal
                    )
                    Image(
                        modifier = modifier
                            .clickable{
                                setBottomSheetVisible(true)
                                setVisibleBottomBar(false)
                            }
                        ,
                        painter = painterResource(R.drawable.menu_dot),
                        contentDescription = "Menu dot"
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 19.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            modifier = Modifier.padding(bottom = 3.dp),
                            text = user.name,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = Gilroy,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "@${user.nickName ?: user.id}",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = Gilroy,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.width(79.dp),
                            text = counterText("$countPost", GetDeclensionOfAWord(
                                num = countPost,
                                one = "пост",
                                two = "поста",
                                five = "постов"
                            )),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.width(79.dp),
                            text = counterText("$countFriend", GetDeclensionOfAWord(
                                num = countFriend ?: 0,
                                one = "друг",
                                two = "друга",
                                five = "друзей"
                            )),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.width(79.dp),
                            text = counterText("$countProduct", GetDeclensionOfAWord(
                                num = countProduct,
                                one = "товар",
                                two = "товара",
                                five = "товаров"
                            )),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.width(79.dp),
                            text = counterText("$countFollowed", GetDeclensionOfAWord(
                                num = countFollowed,
                                one = "фандом",
                                two = "фандома",
                                five = "фандомов"
                            )),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun counterText(count: String, description: String) : AnnotatedString{
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold,
            )
        ) {
            append(count)
            append("\n")
        }
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Light
            )
        ) {
            append(description)
        }
    }
}

@Composable
fun SettingsSheet(
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(40.dp)
                .height(4.dp)
                .background(Gray42.copy(alpha = 0.5f), RoundedCornerShape(2.dp))
        )

//        Text(
//            text = "Настройки профиля",
//            fontSize = 20.sp,
//            fontFamily = Gilroy,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 24.dp)
//        )

        SettingsItem(text = "Выход", isDestructive = true) {
            onClose()
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProfileScreen()
}