package com.mindtoscreen.cappupos.domain.repository

import com.mindtoscreen.cappupos.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(productId: String): Product?
    suspend fun getProductCount(): Int
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(productId: String)
}
