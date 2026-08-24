package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM productentity")
    suspend fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)
}