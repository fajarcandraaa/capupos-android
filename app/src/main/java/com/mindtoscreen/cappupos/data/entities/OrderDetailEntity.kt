package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "order_details",
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
            childColumns = ["productId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class OrderDetailEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val orderId: String,
    val productId: String? = null,
    val quantity: Int = 0,
    val price: Double = 0.0
)
