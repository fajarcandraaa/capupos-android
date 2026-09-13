package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import javax.inject.Inject

/**
 * Use case untuk hapus transaksi. FR-08 / [2026-09-13] poin 6.
 * Status "lunas" -> soft delete (isDeleted=1). Status "belum_bayar" -> hard delete permanen.
 */
class HapusTransaksiUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(orderId: String): Result<Unit> {
        val order = orderRepository.getOrderById(orderId)
            ?: return Result.failure(IllegalStateException("Order tidak ditemukan"))
        return when (order.status) {
            "lunas" -> orderRepository.softDelete(orderId)
            "belum_bayar" -> orderRepository.hardDelete(orderId)
            else -> Result.failure(IllegalStateException("Status order tidak dapat dihapus: ${order.status}"))
        }
    }
}
