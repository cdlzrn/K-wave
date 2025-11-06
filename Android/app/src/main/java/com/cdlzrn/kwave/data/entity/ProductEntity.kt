package com.cdlzrn.kwave.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cdlzrn.kwave.data.enums.Currency

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")            val id: Long = 0L,

    @ColumnInfo(name = "name")          val name: String,
    @ColumnInfo(name = "image")         val image: Int,
    @ColumnInfo(name = "price")         val price: Int,
    @ColumnInfo(name = "currency")      val currency: Currency,
    @ColumnInfo(name = "description")   val description: String,
    @ColumnInfo(name = "delivery_price") val deliveryPrice: Int,
    @ColumnInfo(name = "day_before_delivery") val dayBeforeDelivery: Int,

    @ColumnInfo(name = "seller_id")      val sellerId: Long,
)

//data class ProductWithAllData(
//    @Embedded val product: ProductEntity,
//    @Relation(parentColumn = "seller_id", entityColumn = "id") val seller: UserEntity
//)
