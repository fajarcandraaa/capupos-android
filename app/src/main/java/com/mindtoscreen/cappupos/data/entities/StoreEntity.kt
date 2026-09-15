package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey val id: Long = 1,
    val nama: String,
    val alamat: String,
    val logo: String? = null,
    val kategori: String? = null,
    val deskripsi: String? = null,
    val telepon: String? = null
)