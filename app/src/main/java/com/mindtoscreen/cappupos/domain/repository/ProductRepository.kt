package com.mindtoscreen.cappupos.domain.repository

import com.mindtoscreen.cappupos.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}