package com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.data.model.ProductData
import com.cdlzrn.kwave.data.model.UserForProductData
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.asset.card.ArtistRoundedCard
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray8
import com.cdlzrn.kwave.presentation.utils.GetDeclensionOfAWord
import com.cdlzrn.kwave.presentation.viewmodel.ShopProductViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopProduct(
    modifier: Modifier = Modifier,
    navToHome: () -> Unit,
    navToEmptyCart: () -> Unit,
    navToFillCart: () -> Unit,
    productID: Long = 0,
    setShowOops: (Boolean) -> Unit,
    viewModel: ShopProductViewModel = viewModel<ShopProductViewModel>()
) {

    val productFlow = remember(viewModel, productID) {
        viewModel.productFlow(productID)
    }
    val productState: ProductData? by productFlow.collectAsStateWithLifecycle( initialValue = null )
    val product = productState

    val sellerFlow = remember(viewModel, productState?.sellerId) {
        viewModel.sellerFlow(productState?.sellerId)
    }
    val sellerState: UserForProductData? by sellerFlow.collectAsStateWithLifecycle( initialValue = null )
    val seller = sellerState

    val productIdInCartState: List<Long>? by viewModel.productIdInCartState.collectAsStateWithLifecycle()
    val productIdInCart = productIdInCartState

    val productCountInCartState: Int? by viewModel.productCountInCartState.collectAsStateWithLifecycle()
    val productCountInCart = productCountInCartState

    val inCart = if (productIdInCart != null) productID in productIdInCart else false
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Header(
            countProductInCart = productCountInCart ?: 0,
            navToHome = navToHome,
            navToEmptyCart = navToEmptyCart,
            navToFillCart = navToFillCart,
            setShowOops = setShowOops
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(332.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray8),
                    contentAlignment = Alignment.Center
                ) {
                    if (product != null) {
                        Image(
                            modifier = Modifier
                                .height(250.dp)
                                .shadow(
                                    elevation = 5.dp,
                                    shape = RoundedCornerShape(20.dp),
                                )
                                .clip(RoundedCornerShape(20.dp)),
                            painter = painterResource(product.image),
                            contentDescription = product.name
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Image(
                            modifier = Modifier.padding(top = 13.dp, end = 24.dp),
                            painter = painterResource(R.drawable.bookmark),
                            contentDescription = "Bookmark"
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
//            Row( //TODO: теги добавить
//                modifier = Modifier.padding(top = 9.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            ){
//                product.tags.forEach{ tag ->
//                    Tag(
//                        modifier = Modifier.padding(end = 4.dp),
//                        text = tag
//                    )
//                }
//            }

                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = (product?.price?.toString() + product?.currency?.symbol),
                        color = Gray100,
                        fontSize = 20.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = product?.name ?: "",
                        color = Gray100,
                        fontSize = 14.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.SemiBold
                    )

                    ArtistRoundedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 17.dp),
                        data = ArtistCardItem(
                            name = seller?.name ?: "",
                            imageResId = seller?.avatar ?: -1
                        )
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .height(38.dp)
                                .weight(1f)
                                .padding(end = 8.dp)
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .clip(RoundedCornerShape(15.dp))
                                .background(Gray8),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Image(
                                    modifier = Modifier.padding(end = 8.dp),
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = "Star"
                                )
                                Text(
                                    modifier = Modifier.padding(top = 2.dp),
                                    text = seller?.rate.toString(),
                                    color = Gray100,
                                    fontSize = 16.sp,
                                    fontFamily = Gilroy,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Text(
                                modifier = Modifier.padding(horizontal = 7.dp),
                                text = "Рейтинг продавца",
                                color = Gray100,
                                fontSize = 8.sp,
                                fontFamily = Gilroy,
                                fontWeight = FontWeight.Light
                            )
                        }

                        Row(
                            modifier = Modifier
                                .height(38.dp)
                                .weight(1f)
                                .padding(end = 8.dp)
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .clip(RoundedCornerShape(15.dp))
                                .background(Gray8),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier.padding(end = 4.dp),
                                painter = painterResource(R.drawable.comment),
                                contentDescription = "Comment"
                            )
                            Text(
                                modifier = Modifier.padding(top = 3.dp, end = 4.dp),
                                text = "kek",
                                color = Gray100,
                                fontSize = 16.sp,
                                fontFamily = Gilroy,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                modifier = Modifier.padding(top = 3.dp, end = 8.dp),
                                text = GetDeclensionOfAWord(
                                    num = 1,
                                    one = "Отзыв",
                                    two = "Отзыва",
                                    five = "Отзывов",
                                ),
                                color = Gray100,
                                fontSize = 10.sp,
                                fontFamily = Gilroy,
                                fontWeight = FontWeight.Light
                            )
                        }

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .clip(RoundedCornerShape(15.dp))
                                .background(Gray8),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier.padding(end = 4.dp),
                                painter = painterResource(R.drawable.chat),
                                contentDescription = "Comment"
                            )
                            Text(
                                modifier = Modifier.padding(top = 3.dp, end = 8.dp),
                                text = "Связаться",
                                color = Gray100,
                                fontSize = 10.sp,
                                fontFamily = Gilroy,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Описание",
                        color = Gray100,
                        fontSize = 20.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = product?.description ?: "",
                        color = Gray100,
                        fontSize = 14.sp,
                        fontFamily = Gilroy,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }

        GradientButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            fill = !inCart,
            text = if (inCart) "В корзине" else "Купить сейчас",
            roundSize = 12.dp,
            onClick = {
                if (authUserIdLong == -1L) setShowOops(true)
                else {
                    if (inCart) viewModel.deleteProductFromCart(productID)
                    else {
                        viewModel.addProductToCart(productID)
                        navToFillCart()
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Header(
    modifier: Modifier = Modifier,
    countProductInCart: Int,
    navToHome: () -> Unit,
    navToEmptyCart: () -> Unit,
    navToFillCart: () -> Unit,
    setShowOops: (Boolean) -> Unit,
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
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable {
                    navToHome()
                }
            ,
            painter = painterResource(R.drawable.back_arrow),
            contentDescription = "Back"
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun Preview() {
    ShopProduct(
        navToHome = {},
        navToFillCart = {},
        navToEmptyCart = {},
        setShowOops = {}
    )
}