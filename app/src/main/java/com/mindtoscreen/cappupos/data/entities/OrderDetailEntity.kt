package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["produkId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class OrderDetailEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val orderId: String,
    val produkId: String? = null,
    val namaItem: String = "",
    val qty: Int = 0,
    val hargaSatuan: Double = 0.0
)
