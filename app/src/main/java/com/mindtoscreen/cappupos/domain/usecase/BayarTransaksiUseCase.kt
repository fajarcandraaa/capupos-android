package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import javax.inject.Inject

/**
 * Use case untuk pembayaran transaksi (tunai/non-tunai). FR-06.
 * Kembalian dihitung otomatis dari nominal diterima dikurangi subtotal.
 */
class BayarTransaksiUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(orderId: String, metodeBayar: String, nominalDiterima: Double): Result<Unit> {
        val order = orderRepository.getOrderById(orderId)
            ?: return Result.failure(IllegalStateException("Order tidak ditemukan"))
        if (nominalDiterima < order.subtotal) {
            return Result.failure(IllegalArgumentException("Nominal diterima kurang dari subtotal"))
        }
        val kembalian = nominalDiterima - order.subtotal
        return orderRepository.updatePayment(orderId, metodeBayar, nominalDiterima, kembalian)
    }
}
