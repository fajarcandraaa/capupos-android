package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY urutan ASC")
    suspend fun getAll(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: String): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity)

    @Query("UPDATE categories SET nama = :nama, updatedAt = :timestamp WHERE id = :id")
    suspend fun update(id: String, nama: String, timestamp: Long)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun delete(id: String): Int

    @Query("UPDATE categories SET urutan = :urutan WHERE id = :id")
    suspend fun updateUrutan(id: String, urutan: Int)

    @Query("SELECT COUNT(*) FROM products WHERE kategoriId = :kategoriId AND isDeleted = 0")
    suspend fun countProductsByKategori(kategoriId: String): Int
}
