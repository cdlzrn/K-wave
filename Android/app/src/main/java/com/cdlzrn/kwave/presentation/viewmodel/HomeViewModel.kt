package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.Graph.cartRepository
import com.cdlzrn.kwave.Graph.postRepository
import com.cdlzrn.kwave.Graph.productRepository
import com.cdlzrn.kwave.Graph.userRepository
import com.cdlzrn.kwave.data.model.ArtistCardItem
import com.cdlzrn.kwave.data.model.CartData
import com.cdlzrn.kwave.data.model.PostData
import com.cdlzrn.kwave.data.model.ProductData
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
class HomeViewModel: ViewModel() {

    private var navToShop: (() -> Unit)? = null

    fun setNavToShopAction(action: () -> Unit) {
        this.navToShop = action
    }


    fun getAllFollowed(userId: Long): Flow<List<UserData>> {
        return userRepository.getAllArtistsByUser(userId = userId)
    }

    fun getRandProduct(count: Int): Flow<List<ProductData>> {
        return productRepository.getRandomProduct(count)
    }

    fun getPostItems(userId: Long): Flow<List<PostData>> {
        return postRepository.getFollowedAndUserPosts(userId)
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
                val resultList = artistsList.map { artist ->
                    ArtistCardItem(
                        id = artist.id,
                        name = artist.name,
                        imageResId = artist.avatar
                    )
                }.toMutableList()
                val size = resultList.size

                resultList.add(ArtistCardItem(name = "plus"))

                repeat(2 - size) {
                    resultList.add(ArtistCardItem())
                }
                resultList.toList()

                SliderConfig(
                    label = "Мои фандомы",
                    isHaveArrow = true,
                    items = resultList
                )
            }

    val sliderConfigMerchItemsFlow: Flow<SliderConfig<ShopCardItem>> =
        getRandProduct(5)
            .map { productList ->
                SliderConfig(
                    label = "Мерч",
                    isHaveArrow = true,
                    onClickToArrow = { navToShop?.invoke() },
                    items = productList.map { product ->
                        ShopCardItem(
                            id = product.id,
                            price = product.price,
                            name = product.name,
                            currency = product.currency,
                            imageResId = product.image
                        )
                    }
                )
            }

    val productIdInCartFlow: Flow<List<Long>> =
        getAllProductInCartByUser(authUserIdLong)
            .map { productList -> productList.map { product -> product.productId } }


    val artistsConfigState: StateFlow<SliderConfig<ArtistCardItem>?> =
        sliderConfigArtistItemsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val merchConfigState: StateFlow<SliderConfig<ShopCardItem>?> =
        sliderConfigMerchItemsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val postsState: StateFlow<List<PostData>> =
        getPostItems(authUserIdLong)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val productIdInCartState: StateFlow<List<Long>> =
        productIdInCartFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}