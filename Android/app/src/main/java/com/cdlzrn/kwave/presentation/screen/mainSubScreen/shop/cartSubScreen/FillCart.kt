package com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.cartSubScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.enums.Currency
import com.cdlzrn.kwave.data.model.CartItem
import com.cdlzrn.kwave.presentation.asset.CheckBoxCircle
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.asset.getGradientBrush
import com.cdlzrn.kwave.presentation.asset.getGradientTextStyle
import com.cdlzrn.kwave.presentation.screen.mainSubScreen.shop.ProductInCartCounter
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray25
import com.cdlzrn.kwave.presentation.theme.ui.Gray55
import com.cdlzrn.kwave.presentation.utils.GetDeclensionOfAWord
import com.cdlzrn.kwave.presentation.viewmodel.FillCartViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FillCart(
    modifier: Modifier = Modifier,
    navToPayment: () -> Unit,
    navToProduct: (Long) -> Unit,
    navToShopHome: () -> Unit,
    navToEmptyCart: () -> Unit,
    viewModel: FillCartViewModel = viewModel<FillCartViewModel>()
) {

    val productInCartState: List<CartItem>? by viewModel.productInCartState.collectAsStateWithLifecycle()
    val products = productInCartState

    val productsCheckbox = rememberSaveable(products) {
        mutableStateListOf<Boolean>().apply {
            products?.forEach { product ->
                add(product.isSelected)
            }
        }
    }

    val productsCount = rememberSaveable(products) {
        mutableStateListOf<Int>().apply {
            products?.forEach { product ->
                add(product.countInCart)
            }
        }
    }

    val allCheckbox by remember(productsCheckbox) {
        derivedStateOf {
            productsCheckbox.all { it }
        }
    }

    val totalItemCount by remember(productsCheckbox, productsCount) {
        derivedStateOf {
            productsCheckbox.indices.sumOf { index ->
                if (productsCheckbox[index]) productsCount[index] else 0
            }
        }
    }

    val totalPrice by remember(productsCheckbox, productsCount) {
        derivedStateOf {
            productsCheckbox.indices.sumOf { index ->
                if (productsCheckbox[index] && products != null) {
                    products[index].price * productsCount[index]
                } else 0
            }
        }
    }


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 18.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.Start
    ){
        item{
            Header(
                countProductInCart = productsCount.sum()
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Корзина",
                color = Gray100,
                fontSize = 20.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.padding(start = 16.dp, top = 17.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckBoxCircle(
                    checked = allCheckbox,
                    onClick = {
                        val newValue = !allCheckbox
                        productsCheckbox.replaceAll { newValue }
                        products?.forEach {
                            viewModel.updateCartData(
                                it.id,
                                it.countInCart,
                                newValue
                            )
                        }
                    }
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Выбрать все",
                    style = getGradientTextStyle(),
                    fontSize = 12.sp,
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        itemsIndexed(
            items = products ?: emptyList(),
            key = { index, product -> product.id }
        ) { index, product ->
            ProductCard(
                modifier = Modifier.padding(bottom = 6.dp),
                product = product,
                checked = product.isSelected,
                count = productsCount[index],
                onAddCount = {
                    productsCount[index]++
                    viewModel.updateCartData(
                        product.id,
                        productsCount[index],
                        product.isSelected
                    )
                },
                onSubCount = {
                    productsCount[index]--.coerceAtLeast(0)
                    viewModel.updateCartData(
                        product.id,
                        productsCount[index],
                        product.isSelected
                    )
                },
                onClickCheckbox = {
                    viewModel.updateCartData(
                        product.id,
                        productsCount[index],
                        !product.isSelected
                    )
                },
                onClickTrashcan = {
                    viewModel.deleteProductFromCart(product.id)
                    if (productsCount.size - 1 == 0) navToEmptyCart()
                },
                onClickToProduct = {
                    navToProduct(product.id)
                }
            )
        }

        item{
            Total(
                modifier = Modifier.padding(top = 6.dp),
                navToPayment = navToPayment,
                countProductToBuy = totalItemCount,
                totalPrice = totalPrice
            )
            Image(
                modifier = Modifier
                    .padding(start = 26.dp, top = 30.dp, bottom = 19.dp)
                    .clickable {
                        navToShopHome()
                    },
                painter = painterResource(R.drawable.back_arrow_circle),
                contentDescription = "Back arrow circle"
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    countProductInCart: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 20.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(R.drawable.menu),
            contentDescription = "Menu"
        )
        Image(
            painter = painterResource(R.drawable.shop),
            contentDescription = "Shop"
        )
        Box(
            contentAlignment = Alignment.TopEnd
        ){
            Image(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(R.drawable.cart),
                contentDescription = "Cart"
            )
            if (countProductInCart != 0)
                ProductInCartCounter(
                    modifier = Modifier.padding(top = 8.dp, end = 21.dp),
                    count = countProductInCart
                )
        }
    }
}

@Composable
private fun ProductCard(
    modifier: Modifier = Modifier,
    product: CartItem,
    checked: Boolean,
    count: Int,
    onAddCount: () -> Unit,
    onSubCount: () -> Unit,
    onClickToProduct: () -> Unit,
    onClickCheckbox: () -> Unit,
    onClickTrashcan: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(152.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .padding(16.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ){
            CheckBoxCircle(
                modifier = Modifier.padding(top = 32.dp),
                checked = checked,
                onClick = { onClickCheckbox() }
            )
            Image(
                modifier = Modifier
                    .height(80.dp)
                    .width(71.dp)
                    .padding(start = 7.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable{
                        onClickToProduct()
                    }
                ,
                painter = painterResource(product.imageResId),
                contentDescription = product.name,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
            ){
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = (product.price * count.coerceAtLeast(1)).toString() + product.currency.symbol,
                    style = getGradientTextStyle(),
                    fontSize = 14.sp,
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier,
                    text = product.name,
                    color = Gray100,
                    fontSize = 14.sp,
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier,
            ){
                Image(
                    modifier = Modifier.padding(end = 9.dp),
                    painter = painterResource(R.drawable.bookmark_uncolor),
                    contentDescription = "Bookmark uncolor"
                )
                Image(
                    modifier = Modifier
                        .clickable{
                            onClickTrashcan()
                        }
                    ,
                    painter = painterResource(R.drawable.trashcan),
                    contentDescription = "Trashcan"
                )
            }
            Counter(
                count = count,
                onAdd = onAddCount,
                onSub = onSubCount,
            )
        }
    }
}

//TODO: Анимацию добавить
@Composable
private fun Counter(
    modifier: Modifier = Modifier,
    count: Int,
    onAdd: () -> Unit,
    onSub: () -> Unit
) {
    if (count > 0){
        Row(
            modifier = modifier
                .border(
                    width = 1.dp,
                    brush = getGradientBrush(),
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                modifier = Modifier
                    .padding(start = 12.dp, top = 2.dp, bottom = 2.dp)
                    .clickable {
                        onSub()
                    }
                ,
                painter = painterResource(R.drawable.minus),
                contentDescription = "Minus"
            )
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = count.toString(),
                color = Gray100,
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                modifier = Modifier
                    .padding(top = 4.dp, end = 12.dp, bottom = 4.dp)
                    .size(16.dp)
                    .clickable {
                        onAdd()
                    }
                ,
                painter = painterResource(R.drawable.plus_gradient),
                contentDescription = "Plus gradient"
            )
        }
    }
    else{
        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Gray25,
                    shape = RoundedCornerShape(8.dp)
                )
        ){
            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .size(16.dp)
                    .clickable {
                        onAdd()
                    }
                ,
                painter = painterResource(R.drawable.plus_gradient),
                contentDescription = "Plus gradient"
            )
        }
    }
}


@Composable
private fun Total(
    modifier: Modifier = Modifier,
    navToPayment: () -> Unit,
    countProductToBuy: Int,
    currency: Currency = Currency.RUB,
    totalPrice: Int
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(152.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .padding(12.dp)
        ,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier,
                text = "Ваша корзина",
                color = Gray100,
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier,
                text = "$countProductToBuy ${GetDeclensionOfAWord(
                    num = countProductToBuy,
                    one = "товар",
                    two = "товара",
                    five = "товаров"
                )}",
                color = Gray55,
                fontSize = 10.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier,
                text = "Товары ($countProductToBuy)",
                color = Gray100,
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier,
                text = totalPrice.toString() + currency.symbol,
                color = Gray100,
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 6.dp, bottom = 9.dp),
            thickness = 1.dp,
            color = Gray55
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier,
                text = "Итого",
                color = Gray100,
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier,
                text = totalPrice.toString() + currency.symbol,
                color = Gray100,
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
        }
        GradientButton(
            modifier = Modifier.padding(top = 16.dp),
            fill = true,
            text = "Перейти к оформлению",
            roundSize = 10.dp,
            onClick = {
                navToPayment()
            }
        )
    }
}