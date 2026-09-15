package com.mindtoscreen.cappupos.domain.repository

import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.model.FilterRiwayat
import com.mindtoscreen.cappupos.domain.model.LaporanOverview
import com.mindtoscreen.cappupos.domain.model.StokHistoriItem

interface OrderRepository {
    suspend fun getBelumBayar(): List<Order>
    suspend fun getOrderById(orderId: String): Order?
    suspend fun saveOrder(order: Order): String
    suspend fun updateStatus(orderId: String, status: String, statusPo: String?)

    // TASK-006: Pembayaran
    suspend fun updatePayment(orderId: String, metodeBayar: String, nominalDiterima: Double, kembalian: Double): Result<Unit>

    // TASK-006: Hapus Transaksi
    suspend fun softDelete(orderId: String): Result<Unit>
    suspend fun hardDelete(orderId: String): Result<Unit>

    // TASK-006: Riwayat
    suspend fun getAllOrders(): List<Order>

    // TASK-006: Laporan
    suspend fun getLaporanAggregat(tanggalAwal: Long, tanggalAkhir: Long): Result<LaporanOverview>
    suspend fun getStokHistori(): List<StokHistoriItem>
}
