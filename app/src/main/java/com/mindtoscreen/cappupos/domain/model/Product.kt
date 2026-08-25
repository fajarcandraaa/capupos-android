package com.mindtoscreen.cappupos.domain.model

data class Product(
    val id: String? = null,
    val nama: String = "",
    val foto: String? = null,
    val kategoriId: String? = null,
    val harga: Double = 0.0,
    val deskripsi: String? = null,
    val lacakStok: Boolean = false,
    val jumlahStok: Int? = null,
    val stokMinimal: Int? = null
)
