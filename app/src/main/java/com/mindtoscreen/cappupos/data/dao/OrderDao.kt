package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.OrderEntity

@Dao
interface OrderDao {
    @Query("SELECT * FROM orderentity WHERE status = 'belum_bayar' AND isDeleted = 0 AND isHidden = 0")
    suspend fun getBelumBayar(): List<OrderEntity>

    @Query("SELECT * FROM orderentity WHERE isDeleted = 0")
    suspend fun getAllOrders(): List<OrderEntity>

    @Query("SELECT * FROM orderentity WHERE id = :orderId LIMIT 1")
    suspend fun getById(orderId: String): OrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<OrderEntity>)

    @Query("UPDATE orderentity SET status = :status, metodeBayar = :metode, nominalDiterima = :nominal, kembalian = :kembalian, updatedAt = :updatedAt WHERE id = :orderId")
    suspend fun updatePayment(orderId: String, status: String, metode: String, nominal: Double, kembalian: Double, updatedAt: Long)

    @Query("UPDATE orderentity SET status = :status, statusPo = :statusPo, updatedAt = :updatedAt WHERE id = :orderId")
    suspend fun updateStatus(orderId: String, status: String, statusPo: String?, updatedAt: Long)

    @Query("UPDATE orderentity SET isHidden = 1 WHERE id = :orderId")
    suspend fun hide(orderId: String)

    @Query("UPDATE orderentity SET isDeleted = 1, deletedAt = :deletedAt WHERE id = :orderId AND status = 'lunas'")
    suspend fun softDelete(orderId: String, deletedAt: Long)

    @Query("DELETE FROM orderentity WHERE id = :orderId AND status = 'belum_bayar'")
    suspend fun hardDelete(orderId: String)

    @Query("DELETE FROM orderdetailentity WHERE orderId = :orderId")
    suspend fun deleteItemsForOrder(orderId: String)
}
