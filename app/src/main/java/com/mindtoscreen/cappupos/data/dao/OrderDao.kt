package com.mindtoscreen.cappupos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindtoscreen.cappupos.data.entities.OrderEntity

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders WHERE status = 'belum_bayar' AND isDeleted = 0 AND isHidden = 0")
    suspend fun getBelumBayar(): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE isDeleted = 0")
    suspend fun getAllOrders(): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    suspend fun getById(orderId: String): OrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<OrderEntity>)

    @Query("UPDATE orders SET status = :status, metodeBayar = :metode, nominalDiterima = :nominal, kembalian = :kembalian, updatedAt = :updatedAt WHERE id = :orderId")
    suspend fun updatePayment(orderId: String, status: String, metode: String, nominal: Double, kembalian: Double, updatedAt: Long)

    @Query("UPDATE orders SET status = :status, statusPo = :statusPo, updatedAt = :updatedAt WHERE id = :orderId")
    suspend fun updateStatus(orderId: String, status: String, statusPo: String?, updatedAt: Long)

    @Query("UPDATE orders SET isHidden = :hidden WHERE id = :orderId")
    suspend fun hide(orderId: String, hidden: Boolean = true)

    @Query("UPDATE orders SET isDeleted = 1, deletedAt = :deletedAt WHERE id = :orderId AND status = 'lunas'")
    suspend fun softDelete(orderId: String, deletedAt: Long)

    @Query("DELETE FROM orders WHERE id = :orderId AND status = 'belum_bayar'")
    suspend fun hardDelete(orderId: String)

    @Query("DELETE FROM order_details WHERE orderId = :orderId")
    suspend fun deleteItemsForOrder(orderId: String)
}
