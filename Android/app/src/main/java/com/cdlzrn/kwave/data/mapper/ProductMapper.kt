package com.cdlzrn.kwave.data.mapper

import com.cdlzrn.kwave.data.entity.ProductEntity
import com.cdlzrn.kwave.data.model.ProductData

fun toProductData(product: ProductEntity): ProductData{
    return ProductData(
        id = product.id,
        image = product.image,
        price = product.price,
        name = product.name,
        sellerId = product.sellerId,
        currency = product.currency,
        description = product.description,
    )
}