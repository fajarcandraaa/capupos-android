package com.mindtoscreen.cappupos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val location: String
)