package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.Graph.cartRepository
import com.cdlzrn.kwave.Graph.productRepository
import com.cdlzrn.kwave.Graph.userRepository
import com.cdlzrn.kwave.data.model.CartData
import com.cdlzrn.kwave.data.model.ProductData
import com.cdlzrn.kwave.data.model.UserForProductData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.map

@RequiresApi(Build.VERSION_CODES.O)
class ShopProductViewModel: ViewModel() {

    fun productFlow(productID: Long): Flow<ProductData> {
        return productRepository.getProductById(productID)
    }

    fun sellerFlow(sellerID: Long?): Flow<UserForProductData?> {
        if (sellerID == null) return MutableStateFlow(null)

        return userRepository.getFlowUserByIdForProduct(sellerID)
            .map { it as UserForProductData? }
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


    val productIdInCartFlow: Flow<List<Long>> =
        getAllProductInCartByUser(authUserIdLong)
            .map { productList -> productList.map { product -> product.productId } }

    val productCountInCartFlow: Flow<Int> =
        getAllProductInCartByUser(authUserIdLong)
            .map { productList -> productList.sumOf { it.productCount } }


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
}