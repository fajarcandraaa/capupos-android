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

    @Query("SELECT * FROM productentity WHERE isDeleted = 0")
    suspend fun getActiveProducts(): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM productentity WHERE isDeleted = 0")
    suspend fun countActiveProducts(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("UPDATE productentity SET isDeleted = 1, deletedAt = :timestamp WHERE id = :productId")
    suspend fun softDelete(productId: String, timestamp: Long)

    @Query("DELETE FROM productentity WHERE id = :productId")
    suspend fun hardDelete(productId: String)
}
