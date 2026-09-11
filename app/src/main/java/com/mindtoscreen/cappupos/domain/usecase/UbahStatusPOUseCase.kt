package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import javax.inject.Inject

/**
 * Use case untuk mengubah status PO (5 tahap).
 * FR-05.5: status "selesai" tidak dapat diubah ke "dibatalkan".
 */
class UbahStatusPOUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    companion object {
        val PO_STAGES = listOf("menunggu_konfirmasi", "diproses", "siap", "selesai", "dibatalkan")
    }

    suspend fun execute(orderId: String, statusPoBaru: String): Result<Unit> {
        return try {
            val order = orderRepository.getOrderById(orderId)
                ?: return Result.failure(IllegalStateException("Order tidak ditemukan"))
            if (order.statusPo == "selesai" && statusPoBaru == "dibatalkan") {
                return Result.failure(IllegalStateException("Status selesai tidak dapat diubah ke dibatalkan"))
            }
            orderRepository.updateStatus(orderId, order.status, statusPoBaru)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
