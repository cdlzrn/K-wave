package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
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
import com.cdlzrn.kwave.data.model.ProductForShopData
import com.cdlzrn.kwave.data.model.ShopCardItem
import com.cdlzrn.kwave.data.model.SliderConfig
import com.cdlzrn.kwave.data.model.UserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModel: ViewModel() {

    var followSize by mutableIntStateOf(0)
        private set

    fun getAllFollowed(userId: Long): Flow<List<UserData>> {
        return userRepository.getAllArtistsByUser(userId = userId)
    }

    fun getProductItems(userId: Long): Flow<List<ProductForShopData>> {
        return productRepository.getProductsForShopByUser(userId = userId)
    }

    fun getPostItems(userId: Long): Flow<List<PostData>> {
        return postRepository.getPostsByUser(userId = userId)
    }

    fun getUserData(userId: Long): Flow<UserData> {
        return userRepository.getUserDataById(userId)
    }

    fun getCountFriends(userId: Long): Flow<Int> {
        return userRepository.getFriendUserById(userId).map { list -> list.size }
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
                followSize = resultList.size

                resultList.add(ArtistCardItem(name = "plus"))

                repeat(2 - followSize) {
                    resultList.add(ArtistCardItem())
                }
                resultList.toList()

                SliderConfig(
                    label = "Мои фандомы",
                    isHaveArrow = true,
                    items = resultList
                )
            }

    val sliderConfigProductItemsFlow: Flow<SliderConfig<ShopCardItem>> =
        getProductItems(authUserIdLong)
            .map { artistsList ->
                SliderConfig(
                    label = "Мои товары",
                    isHaveArrow = true,
                    items = artistsList.map { artist ->
                        ShopCardItem(
                            id = artist.id,
                            name = artist.name,
                            imageResId = artist.image,
                            price = artist.price,
                            currency = artist.currency,
                        )
                    }
                )
            }

    val productIdInCartFlow: Flow<List<Long>> =
        getAllProductInCartByUser(authUserIdLong)
            .map { productList -> productList.map { product -> product.productId } }

    val artistsConfigState: StateFlow<SliderConfig<ArtistCardItem>?> = sliderConfigArtistItemsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val shopConfigState: StateFlow<SliderConfig<ShopCardItem>?> = sliderConfigProductItemsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val allPostState: StateFlow<List<PostData>> = getPostItems(authUserIdLong)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val userState: StateFlow<UserData?> = getUserData(authUserIdLong)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    val countFriendState: StateFlow<Int?> = getCountFriends(authUserIdLong)
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
}