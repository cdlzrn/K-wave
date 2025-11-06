package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.Graph.cartRepository
import com.cdlzrn.kwave.Graph.productRepository
import com.cdlzrn.kwave.Graph.userRepository
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.data.model.CartData
import com.cdlzrn.kwave.data.model.ProductForShopData
import com.cdlzrn.kwave.data.model.ShopCardItem
import com.cdlzrn.kwave.data.model.SliderConfig
import com.cdlzrn.kwave.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ShopHomeViewModel: ViewModel() {

    fun getAllFollowed(userId: Long): Flow<List<UserData>> {
        return userRepository.getAllArtistsByUser(userId = userId)
    }

    fun getAllProducts(): Flow<List<ProductForShopData>> {
        return productRepository.getAllProductsForShop()
    }

    fun getAllProductInCartByUser(userId: Long): Flow<List<CartData>> {
        return cartRepository.getAllCartDataByUser(userId)
    }

    fun addProductToCart(productId: Long){
        viewModelScope.launch {
            cartRepository.addProductToCart(productId, authUserIdLong)
        }
    }

    fun deleteProductFromCart(productId: Long){
        viewModelScope.launch {
            cartRepository.deleteProductFromCart(productId, authUserIdLong)
        }
    }

    val sliderConfigArtistItemsFlow: Flow<SliderConfig<ArtistCardItem>> =
        getAllFollowed(authUserIdLong)
            .map { artistsList ->
                SliderConfig(
                    label = "Мои фандомы",
                    isHaveArrow = true,
                    items = artistsList.map { artist ->
                        ArtistCardItem(
                            id = artist.id,
                            name = artist.name,
                            imageResId = artist.avatar
                        )
                    }
                )
            }

    val allMerchFlow: Flow<List<SliderConfig<ShopCardItem>>> =
        getAllProducts()
            .map { productList ->
                productList.groupBy { it.sellerName }.map { (productName, productsInGroup) ->
                    SliderConfig(
                        label = productName,
                        isHaveArrow = true,
                        items = productsInGroup.map { productData ->
                            ShopCardItem(
                                id = productData.id,
                                price = productData.price,
                                name = productData.name,
                                currency = productData.currency,
                                imageResId = productData.image,

                            )
                        },
                    )
                }
            }

    val productIdInCartFlow: Flow<List<Long>> =
        getAllProductInCartByUser(authUserIdLong)
            .map { productList -> productList.map { product -> product.productId } }

    val productCountInCartFlow: Flow<Int> =
        getAllProductInCartByUser(authUserIdLong)
            .map { productList -> productList.sumOf { it.productCount } }


    val artistsConfigState: StateFlow<SliderConfig<ArtistCardItem>?> =
        sliderConfigArtistItemsFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    val productIdInCartState: StateFlow<List<Long>> =
        productIdInCartFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val productCountInCartState: StateFlow<Int> =
        productCountInCartFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )

    val allMerchState: StateFlow<List<SliderConfig<ShopCardItem>>> =
        allMerchFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}