package com.mindtoscreen.cappupos.data.repository

import androidx.room.withTransaction
import com.mindtoscreen.cappupos.data.AppDatabase
import com.mindtoscreen.cappupos.data.dao.OrderDao
import com.mindtoscreen.cappupos.data.dao.OrderDetailDao
import com.mindtoscreen.cappupos.data.dao.ProductDao
import com.mindtoscreen.cappupos.data.dao.StockHistoryDao
import com.mindtoscreen.cappupos.data.entities.OrderDetailEntity
import com.mindtoscreen.cappupos.data.entities.OrderEntity
import com.mindtoscreen.cappupos.data.entities.StockHistoryEntity
import com.mindtoscreen.cappupos.domain.model.DailyTrend
import com.mindtoscreen.cappupos.domain.model.LaporanOverview
import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.model.OrderItem
import com.mindtoscreen.cappupos.domain.model.StokHistoriItem
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val orderDao: OrderDao,
    private val orderDetailDao: OrderDetailDao,
    private val productDao: ProductDao,
    private val stockHistoryDao: StockHistoryDao
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

    // TASK-006: Pembayaran — atomic update payment + stock reduction + stock history.
    override suspend fun updatePayment(
        orderId: String,
        metodeBayar: String,
        nominalDiterima: Double,
        kembalian: Double
    ): Result<Unit> {
        return try {
            val now = System.currentTimeMillis()
            appDatabase.withTransaction {
                orderDao.updatePayment(orderId, "lunas", metodeBayar, nominalDiterima, kembalian, now)
                reduceStockForOrder(orderId, "order_$orderId", now)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * TASK-006: Kurangi stok produk (lacakStok saja) per OrderDetail saat order
     * jadi lunas, lalu catat histori. reason = "order_<orderId>" per DECISIONS.md
     * [2026-09-13] poin 7. Harus dipanggil di dalam transaksi DB.
     */
    private suspend fun reduceStockForOrder(orderId: String, reason: String, now: Long) {
        val details = orderDetailDao.getByOrder(orderId)
        val histories = mutableListOf<StockHistoryEntity>()
        for (detail in details) {
            val productId = detail.productId ?: continue
            val product = productDao.getById(productId) ?: continue
            if (!product.lacakStok) continue
            val before = product.jumlahStok ?: continue
            val after = (before - detail.quantity).coerceAtLeast(0)
            productDao.updateStok(productId, after, now)
            histories.add(
                StockHistoryEntity(
                    productId = productId,
                    quantityBefore = before,
                    quantityAfter = after,
                    reason = reason,
                    timestamp = now
                )
            )
        }
        if (histories.isNotEmpty()) stockHistoryDao.insertAll(histories)
    }

    // TASK-006: Hapus transaksi.
    override suspend fun softDelete(orderId: String): Result<Unit> {
        return try {
            orderDao.softDelete(orderId, System.currentTimeMillis())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hardDelete(orderId: String): Result<Unit> {
        return try {
            appDatabase.withTransaction {
                orderDetailDao.deleteByOrder(orderId)
                orderDao.hardDelete(orderId)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TASK-006: Riwayat.
    override suspend fun getAllOrders(): List<Order> {
        return orderDao.getAllOrders().map { it.toDomain(orderDetailDao.getByOrder(it.id)) }
    }

    // TASK-006: Laporan — agregat in-memory dari orders (volume POS kecil).
    override suspend fun getLaporanAggregat(tanggalAwal: Long, tanggalAkhir: Long): Result<LaporanOverview> {
        return try {
            val orders = orderDao.getAllOrders()
                .filter { it.status == "lunas" && it.tanggal in tanggalAwal..tanggalAkhir }

            val totalPenjualan = orders.sumOf { it.subtotal }
            val metodeTerpopuler = orders.mapNotNull { it.metodeBayar }
                .groupingBy { it }
                .eachCount()
                .maxByOrNull { it.value }?.key

            // Tren per hari: subtotal per tanggal (start-of-day epoch millis).
            val trendPerHari = orders
                .groupBy { startOfDay(it.tanggal) }
                .map { (tanggal, perHari) -> DailyTrend(tanggal, perHari.sumOf { it.subtotal }) }
                .sortedBy { it.tanggal }

            Result.success(
                LaporanOverview(
                    periodAwal = tanggalAwal,
                    periodAkhir = tanggalAkhir,
                    totalPenjualan = totalPenjualan,
                    jumlahTransaksi = orders.size,
                    metodeBayarTerpopuler = metodeTerpopuler,
                    trendPerHari = trendPerHari
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TASK-006: Laporan stok — histori perubahan stok dari alur pembayaran.
    override suspend fun getStokHistori(): List<StokHistoriItem> {
        val histories = stockHistoryDao.getAll()
        val products = productDao.getActiveProducts().associateBy { it.id }
        return histories.map { h ->
            StokHistoriItem(
                id = h.id,
                productId = h.productId,
                productName = products[h.productId]?.nama ?: h.productId,
                quantityBefore = h.quantityBefore,
                quantityAfter = h.quantityAfter,
                reason = h.reason,
                timestamp = h.timestamp
            )
        }
    }

    private fun startOfDay(millis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
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
            namaItem = this.namaItem,
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
            deskripsi = this.deskripsi,
            namaItem = this.namaItem
        )
    }
}
