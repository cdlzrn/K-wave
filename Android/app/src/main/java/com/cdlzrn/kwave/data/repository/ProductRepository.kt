package com.cdlzrn.kwave.data.repository

import com.cdlzrn.kwave.data.dao.ProductDao
import com.cdlzrn.kwave.data.mapper.toProductData
import com.cdlzrn.kwave.data.model.ProductData
import com.cdlzrn.kwave.data.model.ProductForShopData
import com.cdlzrn.kwave.data.model.ProductInCartData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productDao: ProductDao
) {

    fun getAllProductsForShop(): Flow<List<ProductForShopData>>{
        return productDao.getAllProductsForShop()
    }

    fun getProductsForShopByUser(userId: Long): Flow<List<ProductForShopData>>{
        return productDao.getProductsForShopByUser(userId)
    }

    fun getAllProductDataInCartByUser(userId: Long): Flow<List<ProductInCartData>> {
        return productDao.getAllProductDataInCartByUser(userId)
    }

    fun getRandomProduct(count: Int): Flow<List<ProductData>> {
        return productDao.getRandomProducts(count).map { flowList ->
            flowList.map{ entity -> toProductData(entity) }
        }
    }

    fun getProductById(productId: Long): Flow<ProductData> {
        return productDao.getProductById(productId)
    }

    fun getAllSelectedProductByUserId(userId: Long): Flow<List<ProductInCartData>>{
        return productDao.getAllSelectedProductByUserId(userId)
    }

}