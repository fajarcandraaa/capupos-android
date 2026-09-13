package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.StockHistoryEntity

@Dao
interface StockHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: StockHistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(histories: List<StockHistoryEntity>)

    @Query("SELECT * FROM stock_history ORDER BY timestamp DESC")
    suspend fun getAll(): List<StockHistoryEntity>

    @Query("SELECT * FROM stock_history WHERE timestamp BETWEEN :awal AND :akhir ORDER BY timestamp DESC")
    suspend fun getByRange(awal: Long, akhir: Long): List<StockHistoryEntity>

    @Query("SELECT * FROM stock_history WHERE productId = :productId ORDER BY timestamp DESC")
    suspend fun getByProduct(productId: String): List<StockHistoryEntity>
}
