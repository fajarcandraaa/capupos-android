package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerId: Long,
    val total: Double,
    val timestamp: Long
)