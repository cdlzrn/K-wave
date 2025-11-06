package com.cdlzrn.kwave.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cdlzrn.kwave.data.entity.ProductEntity
import com.cdlzrn.kwave.data.model.ProductData
import com.cdlzrn.kwave.data.model.ProductForShopData
import com.cdlzrn.kwave.data.model.ProductInCartData
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<ProductEntity>)

    @Query("""
        SELECT
            p.id AS id,
            p.image AS image,
            p.price AS price,
            p.name AS name,
            p.seller_id AS sellerId,
            p.currency AS currency,
            p.description AS description,
            p.delivery_price AS deliveryPrice,
            p.day_before_delivery AS dayBeforeDelivery
        FROM product AS p
        WHERE p.id = :productId
    """)
    fun getProductById(productId: Long): Flow<ProductData>

    @Query("""
        SELECT * 
        FROM product 
        ORDER BY RANDOM() 
        LIMIT :count
    """)
    fun getRandomProducts(count: Int): Flow<List<ProductEntity>>

    @Query("""
        SELECT 
            p.id AS id,
            p.image AS image,
            p.price AS price,
            p.name AS name,
            u.name AS sellerName,
            p.currency AS currency
        FROM product AS p
        INNER JOIN user AS u ON p.seller_id = u.id
    """)
    fun getAllProductsForShop(): Flow<List<ProductForShopData>>

    @Query("""
        SELECT 
            p.id AS id,
            p.image AS image,
            p.price AS price,
            p.name AS name,
            u.name AS sellerName,
            p.currency AS currency
        FROM product AS p
        INNER JOIN user AS u ON p.seller_id = u.id
        WHERE u.id = :userId
    """)
    fun getProductsForShopByUser(userId: Long): Flow<List<ProductForShopData>>

    @Query("""
    SELECT
        p.id AS id,
        p.name AS name,
        p.image AS image,
        p.price AS price,
        p.currency AS currency,
        c.product_count AS countInCart,
        p.delivery_price AS deliveryPrice,
        p.day_before_delivery AS dayBeforeDelivery,
        c.is_selected AS isSelected
    FROM product AS p
    INNER JOIN cart AS c ON c.product_id = p.id
    WHERE c.user_id = :userId
    """)
    fun getAllProductDataInCartByUser(userId: Long): Flow<List<ProductInCartData>>

    @Query("""
    SELECT
        p.id AS id,
        p.name AS name,
        p.image AS image,
        p.price AS price,
        p.currency AS currency,
        c.product_count AS countInCart,
        p.delivery_price AS deliveryPrice,
        p.day_before_delivery AS dayBeforeDelivery,
        c.is_selected AS isSelected
        
    FROM product AS p
    INNER JOIN cart AS c ON c.product_id = p.id
    WHERE c.user_id = :userId AND c.is_selected
    """)
    fun getAllSelectedProductByUserId(userId: Long): Flow<List<ProductInCartData>>
}