package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.OrderDetailEntity

@Dao
interface OrderDetailDao {
    @Query("SELECT * FROM orderdetailentity WHERE orderId = :orderId")
    suspend fun getByOrder(orderId: String): List<OrderDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: OrderDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<OrderDetailEntity>)

    @Query("DELETE FROM orderdetailentity WHERE orderId = :orderId")
    suspend fun deleteByOrder(orderId: String)

    @Query("SELECT SUM(hargaSatuan * qty) FROM orderdetailentity WHERE orderId = :orderId")
    suspend fun getSubtotal(orderId: String): Double?
}
