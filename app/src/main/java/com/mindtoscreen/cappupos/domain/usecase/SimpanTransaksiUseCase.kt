package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import javax.inject.Inject

/**
 * Use case untuk menyimpan transaksi (Simpan/Open Bill) sebagai "Belum Bayar".
 * FR-04, FR-07: Sistem harus memungkinkan transaksi tertunda disimpan.
 */
class SimpanTransaksiUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(order: Order): Result<String> {
        return try {
            if (order.items.isEmpty()) {
                return Result.failure(IllegalArgumentException("Keranjang kosong"))
            }
            val subtotal = order.items.sumOf { it.price * it.quantity }
            val id = orderRepository.saveOrder(order.copy(status = "belum_bayar", subtotal = subtotal))
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
