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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.enums.Currency
import com.cdlzrn.kwave.data.model.PaymentMethod
import com.cdlzrn.kwave.data.model.ProductInCartData
import com.cdlzrn.kwave.presentation.asset.GradientButton
import com.cdlzrn.kwave.presentation.asset.getGradientBrush
import com.cdlzrn.kwave.presentation.theme.ui.Gilroy
import com.cdlzrn.kwave.presentation.theme.ui.Gray100
import com.cdlzrn.kwave.presentation.theme.ui.Gray12
import com.cdlzrn.kwave.presentation.theme.ui.Gray25
import com.cdlzrn.kwave.presentation.theme.ui.Gray55
import com.cdlzrn.kwave.presentation.theme.ui.Price
import com.cdlzrn.kwave.presentation.utils.GetDeclensionOfAWord
import com.cdlzrn.kwave.presentation.utils.getDeliveryDate
import com.cdlzrn.kwave.presentation.viewmodel.PaymentViewModel

enum class DeliveryType {
    PICKUP, COURIER
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Payment(
    modifier: Modifier = Modifier,
    navToBack: () -> Unit,
    navToThanks: () -> Unit,
    viewModel: PaymentViewModel = viewModel<PaymentViewModel>()
) {
    val productsState: List<ProductInCartData>? by viewModel.selectedProductState.collectAsStateWithLifecycle()
    val products = productsState
    
    val methods: List<PaymentMethod> = listOf(
        PaymentMethod(id = 0, imageResID = R.drawable.sber, name = ""),
        PaymentMethod(id = 1, imageResID = R.drawable.sbp, name = ""),
        PaymentMethod(id = 2, imageResID = R.drawable.new_card, name = "")
    )
    
    val totalProductCount: Int? by remember(productsState?.size) {
        mutableStateOf(
            products?.sumOf { it.countInCart }
        )
    }
    val totalDeliveryPrice: Int? by remember(productsState?.size) {
        mutableStateOf(
            products?.sumOf { it.deliveryPrice }
        )
    }
    val totalPrice: Int? by remember(productsState?.size) {
        mutableStateOf(
            products?.sumOf { it.price * it.countInCart }
        )
    }

    var deliveryType by remember { mutableStateOf(DeliveryType.PICKUP ) }

    var selectedMethodId by remember { mutableLongStateOf(-1L) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Header(
            modifier = Modifier
                .padding(bottom = 16.dp)
            ,
            navToCart = navToBack
        )

        LazyColumn(
            modifier = modifier
                .weight(1f)
        ){
            item{
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 13.dp),
                    text = "Доставка",
                    color = Gray100,
                    fontSize = 20.sp,
                    fontFamily = Gilroy,
                    fontWeight = FontWeight.SemiBold
                )

                ReceiveProducts(
                    modifier = Modifier.padding(bottom = 12.dp),
                    deliveryType = deliveryType,
                    changeDeliveryType = { type -> deliveryType = type }
                )

                PaymentMethod(
                    modifier = Modifier.padding(bottom = 12.dp),
                    methods = methods,
                    setSelectedMethod = { id -> selectedMethodId = id},
                    selectedMethodId = selectedMethodId
                )
            }

            if (products != null)
                items(products) { product ->
                    ProductDeliveryInfo(
                        modifier = Modifier.padding(bottom = 12.dp),
                        product = product,
                        deliveryType = deliveryType
                    )
                }
        }

        Total(
            totalPrice = totalPrice ?: 0,
            toPay = {
                viewModel.deleteAllSelectedProduct()
                navToThanks()
            },
            totalProductCount = totalProductCount ?: 0,
            totalDeliveryPrice = totalDeliveryPrice ?: 0,
            selectedMethodId = selectedMethodId
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    navToCart: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp
            )
            .background(Color.White)
            .padding(top = 67.dp, bottom = 27.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
        )
        Text(
            text = "Оформление заказа",
            color = Gray100,
            fontSize = 16.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold
        )
        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable {
                    navToCart()
                },
            painter = painterResource(R.drawable.close_gray),
            contentDescription = "Close"
        )

    }
}

@Composable
fun ReceiveProducts(
    modifier: Modifier = Modifier,
    deliveryType: DeliveryType,
    changeDeliveryType: (DeliveryType) -> Unit
) {
   Column(
       modifier = modifier
           .fillMaxWidth()
           .shadow(
               elevation = 2.dp,
               shape = RoundedCornerShape(12.dp)
           )
           .background(Color.White)
           .padding(16.dp)
       ,
   ){
       DeliverType(
           modifier = Modifier.padding(bottom = 16.dp),
           deliveryType = deliveryType,
           changeDeliveryType = changeDeliveryType
       )

       Row(
            verticalAlignment = Alignment.CenterVertically
       ){
           Image(
               modifier = Modifier.padding(end = 8.dp),
               painter = painterResource(R.drawable.location),
               contentDescription = "Location"
           )

           Column{
               Text(
                   modifier = Modifier.padding(bottom = 4.dp),
                   text = "Пункт выдачи",
                   color = Gray100,
                   fontSize = 14.sp,
                   fontFamily = Gilroy,
                   fontWeight = FontWeight.SemiBold
               )
               Text(
                   text = "Москва, наб Овчинниковская, дом 22/24 стр.1",
                   color = Gray100,
                   fontSize = 12.sp,
                   fontFamily = Gilroy,
                   fontWeight = FontWeight.Light
               )
           }
       }

       Text(
           modifier = Modifier.padding(start = 32.dp, top = 16.dp, bottom = 16.dp),
           text = "Срок хранения - 7 дней",
           color = Gray100,
           fontSize = 12.sp,
           fontFamily = Gilroy,
           fontWeight = FontWeight.Light
       )

       GradientButton(
           modifier = Modifier,
           fill = false,
           text = "Изменить адрес",
           roundSize = 15.dp,
           height = 38.dp,
           onClick = { },
       )
   }
}

@Composable
fun DeliverType(
    modifier: Modifier = Modifier,
    deliveryType: DeliveryType,
    changeDeliveryType: (DeliveryType) -> Unit
) {
    val textStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(7.dp))
            .background(Gray12)
        ,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Box(
            modifier = Modifier
                .weight(.5f)
                .clip(RoundedCornerShape(7.dp))
                .then(
                    if (deliveryType == DeliveryType.PICKUP)
                        Modifier.background(getGradientBrush())
                    else
                        Modifier.background(Gray12)
                )
                .clickable {
                    changeDeliveryType(DeliveryType.PICKUP)
                }
                .padding(vertical = 5.dp)
            ,
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier,
                text = "Самовывоз",
                color = if (deliveryType == DeliveryType.PICKUP) Color.White
                        else Gray100,
                style = textStyle
            )
        }
        Box(
            modifier = Modifier
                .weight(.5f)
                .clip(RoundedCornerShape(7.dp))
                .then(
                    if (deliveryType == DeliveryType.COURIER)
                        Modifier.background(getGradientBrush())
                    else
                        Modifier.background(Gray12)
                )
                .clickable {
                    changeDeliveryType(DeliveryType.COURIER)
                }
                .padding(vertical = 5.dp)
            ,
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier,
                text = "Курьером",
                color = if (deliveryType == DeliveryType.PICKUP) Gray100
                        else Color.White,
                style = textStyle
            )
        }
    }
}

@Composable
fun PaymentMethod(
    modifier: Modifier = Modifier,
    methods: List<PaymentMethod>,
    setSelectedMethod: (Long) -> Unit,
    selectedMethodId: Long
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White)
        ,
    ){
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 7.dp),
            text = "Способ оплаты",
            color = Gray100,
            fontSize = 14.sp,
            fontFamily = Gilroy,
            fontWeight = FontWeight.SemiBold
        )


        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentPadding = PaddingValues(horizontal = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ){
            itemsIndexed(methods) {index, method ->
                Box(
                    modifier = Modifier
                        .size(height = 68.dp, width = 116.dp)
                        .background(Color.White)
                        .padding(2.dp)
                        .clickable {
                            setSelectedMethod(method.id)
                        }
                        .then(
                            if (selectedMethodId == method.id) {
                                Modifier
                                    .border(
                                        width = 2.dp,
                                        brush = getGradientBrush(),
                                        shape = RoundedCornerShape(5.dp)
                                    )
                            } else {
                                Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Gray25,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                            }
                        )
                ){
                    Image(
                        modifier = Modifier.clip(RoundedCornerShape(5.dp)),
                        painter = painterResource(method.imageResID),
                        contentDescription = method.name
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDeliveryInfo(
    modifier: Modifier = Modifier,
    product: ProductInCartData,
    deliveryType: DeliveryType
) {
    val deliveryTo = if (deliveryType == DeliveryType.COURIER) "Курьером" else "в Пункт Выдачи"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White)
            .padding(16.dp)
        ,
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                ,
                painter = painterResource(product.image),
                contentDescription = product.name
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = (product.price * product.countInCart).toString() + product.currency.symbol,
                color = Gray100,
                fontSize = 12.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Medium
            )
        }
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ){
            Text(
                modifier = Modifier,
                text = "Ожидаемая дата доставки:",
                color = Gray100,
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "${getDeliveryDate(product.dayBeforeDelivery)}, ${product.deliveryPrice}${product.currency.symbol}",
                color = Gray100,
                fontSize = 14.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Доставка $deliveryTo",
                color = Gray100,
                fontSize = 10.sp,
                fontFamily = Gilroy,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun Total(
    modifier: Modifier = Modifier,
    toPay: () -> Unit,
    totalProductCount: Int,
    totalPrice: Int,
    totalDeliveryPrice: Int,
    currency: Currency = Currency.RUB,
    selectedMethodId: Long
) {
    val textStyle10dp = TextStyle(
        color = Gray55,
        fontSize = 10.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.Light
    )

    val textStyle12dp = TextStyle(
        fontSize = 12.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium)

    val textStyle14dp = TextStyle(
        color = Gray100,
        fontSize = 14.sp,
        fontFamily = Gilroy,
        fontWeight = FontWeight.SemiBold
    )

    Column (
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .background(Color.White)
            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 35.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier,
                text = "Ваш заказ",
                style = textStyle14dp
            )
            Text(
                modifier = Modifier,
                text = "$totalProductCount ${GetDeclensionOfAWord(
                    num = totalProductCount,
                    one = "товар",
                    two = "товара",
                    five = "товаров",
                )}",
                style = textStyle10dp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier,
                text = "Товары ($totalProductCount)",
                style = textStyle12dp,
                color = Gray100,
            )
            Text(
                modifier = Modifier,
                text = totalPrice.toString() + currency.symbol,
                style = textStyle12dp,
                color = Gray100,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier,
                text = "Стоимость доставки",
                style = textStyle12dp,
                color = Gray100,
            )
            Text(
                modifier = Modifier,
                text = totalDeliveryPrice.toString() + currency.symbol,
                style = textStyle12dp,
                color = Price,
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 9.dp),
            thickness = 1.dp,
            color = Gray55
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier,
                text = "Итого",
                style = textStyle14dp
            )
            Text(
                modifier = Modifier,
                text = ( totalPrice + totalDeliveryPrice ).toString() + currency.symbol,
                color = Gray100,
                style = textStyle14dp
            )
        }
        GradientButton(
            modifier = Modifier.padding(top = 16.dp),
            fill = true,
            text = "Оплатить",
            roundSize = 12.dp,
            onClick = {
                if (selectedMethodId != -1L)
                    toPay()
            },
        )
    }
}