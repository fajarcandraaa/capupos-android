package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class CategoryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nama: String = "",
    val urutan: Int = 0,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
