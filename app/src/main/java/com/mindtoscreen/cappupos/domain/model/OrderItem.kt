package com.mindtoscreen.cappupos.domain.model

data class OrderItem(
    val id: String? = null,
    val productId: String? = null,
    val deskripsi: String? = null,
    val namaItem: String? = null,
    val quantity: Int = 1,
    val price: Double = 0.0
)
