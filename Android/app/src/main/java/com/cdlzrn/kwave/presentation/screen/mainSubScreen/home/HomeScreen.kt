package com.cdlzrn.kwave.presentation.screen.mainSubScreen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.data.model.PostData
import com.cdlzrn.kwave.data.model.ShopCardItem
import com.cdlzrn.kwave.data.model.SliderConfig
import com.cdlzrn.kwave.presentation.asset.RowSlider
import com.cdlzrn.kwave.presentation.asset.card.ArtistCircleCard
import com.cdlzrn.kwave.presentation.asset.card.PostCard
import com.cdlzrn.kwave.presentation.asset.card.ShopCard
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.theme.ui.GochiHand
import com.cdlzrn.kwave.presentation.theme.ui.Gray25
import com.cdlzrn.kwave.presentation.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navToShop: () -> Unit,
    navToProduct: (Long) -> Unit,
    setShowOops: (Boolean) -> Unit,
    viewModel: HomeViewModel = viewModel<HomeViewModel>()
) {

    viewModel.setNavToShopAction(navToShop)

    val artistsConfigState: SliderConfig<ArtistCardItem>? by viewModel.artistsConfigState.collectAsStateWithLifecycle()
    val artistsConfig = artistsConfigState

    val merchConfigState: SliderConfig<ShopCardItem>? by viewModel.merchConfigState.collectAsStateWithLifecycle()
    val merchConfig = merchConfigState

    val postsItemsState: List<PostData>? by viewModel.postsState.collectAsStateWithLifecycle()
    val postsItems = postsItemsState

    val productIdInCartState: List<Long>? by viewModel.productIdInCartState.collectAsStateWithLifecycle()
    val productIdInCart = productIdInCartState


    Column(
        modifier = modifier
    ) {
        Header()

        LazyColumn {

            item {
                if (artistsConfig != null) {
                    RowSlider(
                        modifier = Modifier.padding(bottom = 36.dp),
                        contentPadding = 32.dp,
                        config = artistsConfig
                    ) { index, data ->
                        ArtistCircleCard(data = data)
                    }
                }

                if (merchConfig != null && productIdInCart != null) {
                    RowSlider(
                        modifier = Modifier.padding(bottom = 16.dp),
                        config = merchConfig,
                    ) { item ->
                        ShopCard(
                            data = item,
                            onBuyClick = { productId, isAdd ->
                                if (authUserIdLong == -1L){
                                    setShowOops(true)
                                    false
                                }
                                else {
                                    if (isAdd) viewModel.addProductToCart(productId = productId)
                                    else viewModel.deleteProductFromCart(productId = productId)
                                    true
                                }
                            },
                            inCart = item.id in productIdInCart,
                            onCardClick = { navToProduct(item.id) }
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 16.dp),
                    thickness = 1.dp,
                    color = Gray25,
                )
            }

            if (postsItems != null) {
                items(postsItems) { post ->
                    PostCard(
                        modifier = Modifier.padding(bottom = 16.dp),
                        data = post
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
            .padding(top = 20.dp, bottom = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.padding(start = 22.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo"
        )
        Text(
            text = "K-wave",
            style = getGradientTextStyle(),
            fontSize = 36.sp,
            fontFamily = GochiHand,
            fontWeight = FontWeight.Light
        )
        Image(

            modifier = Modifier.padding(end = 22.dp),
            painter = painterResource(R.drawable.notification),
            contentDescription = "Notification"
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewMainScreen() {
//    HomeScreen()
//}