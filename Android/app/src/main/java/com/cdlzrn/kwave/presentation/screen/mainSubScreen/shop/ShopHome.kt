package com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.data.model.ShopCardItem
import com.cdlzrn.kwave.data.model.SliderConfig
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.asset.RowSlider
import com.cdlzrn.kwave.presentation.asset.card.ArtistRoundedCard
import com.cdlzrn.kwave.presentation.asset.card.ShopCard
import com.cdlzrn.kwave.presentation.viewmodel.ShopHomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopHome(
    modifier: Modifier = Modifier,
    navToProduct: (Long) -> Unit,
    navToEmptyCart: () -> Unit,
    navToFillCart: () -> Unit,
    setShowOops: (Boolean) -> Unit,
    viewModel: ShopHomeViewModel = viewModel<ShopHomeViewModel>(),
) {
    val artistsConfigState: SliderConfig<ArtistCardItem>? by viewModel.artistsConfigState.collectAsStateWithLifecycle()
    val artistsConfig = artistsConfigState

    val shopConfigsState: List<SliderConfig<ShopCardItem>>? by viewModel.allMerchState.collectAsStateWithLifecycle()
    val shopConfigs = shopConfigsState

    val productIdInCartState: List<Long>? by viewModel.productIdInCartState.collectAsStateWithLifecycle()
    val productIdInCart = productIdInCartState

    val productCountInCartState: Int? by viewModel.productCountInCartState.collectAsStateWithLifecycle()
    val productCountInCart = productCountInCartState

    LazyColumn(
        modifier = modifier
            .background(Color.White)
    ){
        item{
            Header(
                countProductInCart = productCountInCart ?: 0,
                navToEmptyCart = navToEmptyCart,
                navToFillCart = navToFillCart,
                setShowOops = setShowOops
            )

            if (artistsConfig != null) {
                RowSlider(
                    modifier = Modifier
                        .padding(vertical = 24.dp),
                    config = artistsConfig
                ) { item ->
                    ArtistRoundedCard(
                        data = item
                    )
                }
            }
        }

        if (productIdInCart != null && shopConfigs != null) {
            items(shopConfigs) { config ->
                RowSlider(
                    config = config
                ) { item ->
                    ShopCard(
                        data = item,
                        onBuyClick = { productId, isAdd ->
                            if (authUserIdLong == -1L) {
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
                GradientButton(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 24.dp),
                    fill = false,
                    text = "Посмотреть все",
                    roundSize = 10.dp,
                    isHaveArrow = true,
                    onClick = {}
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Header(
    modifier: Modifier = Modifier,
    countProductInCart: Int,
    navToEmptyCart: () -> Unit,
    navToFillCart: () -> Unit,
    setShowOops: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 20.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(R.drawable.menu),
            contentDescription = "Menu"
        )
        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.shop),
            contentDescription = "Shop"
        )
        Box(
            modifier = Modifier.padding(end = 16.dp),
            contentAlignment = Alignment.TopEnd
        ){
            Image(
                modifier = Modifier
                    .clickable{
                        if (authUserIdLong == -1L) setShowOops(true)
                        else {
                            if (countProductInCart == 0) navToEmptyCart()
                            else navToFillCart()
                        }
                    },
                painter = painterResource(R.drawable.cart),
                contentDescription = "Cart"
            )
            if (countProductInCart != 0)
                ProductInCartCounter(
                    modifier = Modifier.padding(top = 8.dp, end = 5.dp),
                    count = countProductInCart
                )
        }
    }
}