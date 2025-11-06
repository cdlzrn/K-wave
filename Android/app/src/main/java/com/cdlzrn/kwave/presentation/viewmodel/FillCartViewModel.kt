package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.Graph.cartRepository
import com.cdlzrn.kwave.Graph.productRepository
import com.cdlzrn.kwave.data.model.CartItem
import com.cdlzrn.kwave.data.model.ProductInCartData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class FillCartViewModel: ViewModel() {

    fun getAllProductInCartByUser(userId: Long): Flow<List<ProductInCartData>> {
        return productRepository.getAllProductDataInCartByUser(userId)
    }

    fun updateCartData(productId: Long, count: Int, isSelected: Boolean){
        viewModelScope.launch{
            cartRepository.updateCartData(productId, authUserIdLong, count, isSelected)
        }
    }

    fun deleteProductFromCart(productId: Long){
        viewModelScope.launch {
            cartRepository.deleteProductFromCart(productId, authUserIdLong)
        }
    }

    val productInCartFlow: Flow<List<CartItem>> =
        getAllProductInCartByUser(authUserIdLong)
            .map { productList ->
                productList.map { product ->
                    CartItem(
                        id = product.id,
                        price = product.price,
                        name = product.name,
                        currency = product.currency,
                        imageResId = product.image,
                        countInCart = product.countInCart,
                        deliveryPrice = product.deliveryPrice,
                        isSelected = product.isSelected
                    )
                }
            }

    val productInCartState: StateFlow<List<CartItem>> =
        productInCartFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}