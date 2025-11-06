package com.cdlzrn.kwave.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "cart",
    primaryKeys = ["user_id", "product_id"],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["product_id"])
    ]
)
data class CartEntity(
    @ColumnInfo(name = "user_id")       val userId: Long,
    @ColumnInfo(name = "product_id")    val productId: Long,
    @ColumnInfo(name = "product_count") val productCount: Int,
    @ColumnInfo(name = "is_selected")   val isSelected: Boolean
)