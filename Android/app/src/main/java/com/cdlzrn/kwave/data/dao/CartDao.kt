package com.cdlzrn.kwave.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cdlzrn.kwave.data.entity.CartEntity
import com.cdlzrn.kwave.data.model.CartData
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProductToCart(data: CartEntity)

    @Update
    suspend fun updateCartData(data: CartEntity)

    @Query("""
        SELECT
            c.user_id AS userId,
            c.product_id AS productId,
            c.product_count AS productCount,
            c.is_selected AS isSelected
        FROM cart AS c
        WHERE user_id = :userId
    """)
    fun getAllCartDataByUser(userId: Long): Flow<List<CartData>>


    @Query("""
        DELETE FROM cart 
        WHERE user_id = :userId AND is_selected
    """)
    suspend fun deleteAllSelectedProduct(userId: Long)

    @Delete
    suspend fun deleteProductFromCart(data: CartEntity)

}