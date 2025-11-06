package com.cdlzrn.kwave.data.repository

import com.cdlzrn.kwave.data.dao.CartDao
import com.cdlzrn.kwave.data.entity.CartEntity
import com.cdlzrn.kwave.data.mapper.toCartEntity
import com.cdlzrn.kwave.data.model.CartData
import kotlinx.coroutines.flow.Flow

class CartRepository(
    private val cartDao: CartDao
) {

    suspend fun addProductToCart(productId: Long, userId: Long){
        val data = CartData(
            userId = userId,
            productId = productId,
            productCount = 1,
            isSelected = true
        )
        cartDao.addProductToCart(toCartEntity(data))
    }

    suspend fun updateCartData(productId: Long, userId: Long, count: Int, isSelected: Boolean){
        cartDao.updateCartData(
            CartEntity(
                userId = userId,
                productId = productId,
                productCount = count,
                isSelected = isSelected
            )
        )
    }

    fun getAllCartDataByUser(userId: Long): Flow<List<CartData>> {
        return cartDao.getAllCartDataByUser(userId)
    }

    suspend fun deleteProductFromCart(productId: Long, userId: Long){
        val data = CartData(
            userId = userId,
            productId = productId,
            productCount = -1,
            isSelected = true
        )
        cartDao.deleteProductFromCart(toCartEntity(data))
    }

    suspend fun deleteAllSelectedProduct(userId: Long){
        cartDao.deleteAllSelectedProduct(userId)
    }

}