package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nama: String = "",
    val foto: String? = null,
    val kategoriId: String? = null,
    val harga: Double = 0.0,
    val deskripsi: String? = null,
    val lacakStok: Boolean = false,
    val jumlahStok: Int? = null,
    val stokMinimal: Int? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null
)
