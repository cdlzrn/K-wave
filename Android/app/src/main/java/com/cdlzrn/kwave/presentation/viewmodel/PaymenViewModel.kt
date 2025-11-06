package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.Graph.cartRepository
import com.cdlzrn.kwave.Graph.productRepository
import com.cdlzrn.kwave.data.model.ProductInCartData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class PaymentViewModel: ViewModel() {

    fun getSelectedProductInCart(): Flow<List<ProductInCartData>>{
        return productRepository.getAllSelectedProductByUserId(authUserIdLong)
    }

    fun deleteAllSelectedProduct(){
        viewModelScope.launch {
            cartRepository.deleteAllSelectedProduct(authUserIdLong)
        }
    }

    val selectedProductState: StateFlow<List<ProductInCartData>> =
        getSelectedProductInCart()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}