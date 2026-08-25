package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class OrderEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val status: String = "belum_bayar",
    val statusPo: String? = null,
    val metodeBayar: String? = null,
    val subtotal: Double = 0.0,
    val nominalDiterima: Double? = null,
    val kembalian: Double? = null,
    val catatan: String? = null,
    val tanggal: Long = 0L,
    val isHidden: Boolean = false,
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
