package com.mindtoscreen.cappupos.data.repository

import androidx.room.withTransaction
import com.mindtoscreen.cappupos.data.AppDatabase
import com.mindtoscreen.cappupos.data.dao.OrderDao
import com.mindtoscreen.cappupos.data.dao.OrderDetailDao
import com.mindtoscreen.cappupos.data.entities.OrderDetailEntity
import com.mindtoscreen.cappupos.data.entities.OrderEntity
import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.model.OrderItem
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import java.util.UUID
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val orderDao: OrderDao,
    private val orderDetailDao: OrderDetailDao
) : OrderRepository {

    override suspend fun getBelumBayar(): List<Order> {
        return orderDao.getBelumBayar().map { it.toDomain(orderDetailDao.getByOrder(it.id)) }
    }

    override suspend fun getOrderById(orderId: String): Order? {
        val entity = orderDao.getById(orderId) ?: return null
        return entity.toDomain(orderDetailDao.getByOrder(orderId))
    }

    override suspend fun saveOrder(order: Order): String {
        val now = System.currentTimeMillis()
        val id = order.id ?: UUID.randomUUID().toString()
        // Atomic: order + detail harus ter-save bersama, bukan sebagian (data-loss guard).
        appDatabase.withTransaction {
            orderDao.insert(order.toEntity(id, now))
            orderDetailDao.deleteByOrder(id)
            orderDetailDao.insertAll(order.items.map { it.toEntity(id) })
        }
        return id
    }

    override suspend fun updateStatus(orderId: String, status: String, statusPo: String?) {
        orderDao.updateStatus(orderId, status, statusPo, System.currentTimeMillis())
    }

    private fun OrderEntity.toDomain(details: List<OrderDetailEntity>): Order {
        return Order(
            id = this.id,
            status = this.status,
            statusPo = this.statusPo,
            metodeBayar = this.metodeBayar,
            subtotal = this.subtotal,
            nominalDiterima = this.nominalDiterima,
            kembalian = this.kembalian,
            catatan = this.catatan,
            tanggal = this.tanggal,
            items = details.map { it.toDomain() }
        )
    }

    private fun OrderDetailEntity.toDomain(): OrderItem {
        return OrderItem(
            id = this.id,
            productId = this.productId,
            deskripsi = this.deskripsi,
            quantity = this.quantity,
            price = this.price
        )
    }

    private fun Order.toEntity(id: String, now: Long): OrderEntity {
        return OrderEntity(
            id = id,
            status = this.status,
            statusPo = this.statusPo,
            metodeBayar = this.metodeBayar,
            subtotal = this.subtotal,
            nominalDiterima = this.nominalDiterima,
            kembalian = this.kembalian,
            catatan = this.catatan,
            tanggal = if (this.tanggal == 0L) now else this.tanggal,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun OrderItem.toEntity(orderId: String): OrderDetailEntity {
        return OrderDetailEntity(
            id = this.id ?: UUID.randomUUID().toString(),
            orderId = orderId,
            productId = this.productId,
            quantity = this.quantity,
            price = this.price,
            deskripsi = this.deskripsi
        )
    }
}
