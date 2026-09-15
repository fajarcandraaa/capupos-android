package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.model.Store
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import com.mindtoscreen.cappupos.domain.repository.StoreRepository
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Use case untuk generate struk digital (plain text). FR-11.
 * Header nama+alamat dari Store, body item/subtotal/metode bayar/kembalian.
 * Format currency konsisten dengan UI lain ("Rp <format>").
 */
class GenerateStrukUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val storeRepository: StoreRepository
) {
    suspend fun execute(orderId: String): Result<String> {
        return try {
            val order = orderRepository.getOrderById(orderId)
                ?: return Result.failure(IllegalStateException("Order tidak ditemukan"))

            val store = storeRepository.getStore()
            val harga = NumberFormat.getIntegerInstance(Locale("id", "ID"))
            val tanggalFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("id", "ID"))

            val sb = StringBuilder()
            store?.let {
                sb.appendLine(it.nama)
                if (!it.alamat.isBlank()) sb.appendLine(it.alamat)
                if (!it.telepon.isNullOrBlank()) sb.appendLine(it.telepon)
            }
            sb.appendLine("------------------------------")
            sb.appendLine("Tanggal : ${tanggalFormat.format(Date(order.tanggal))}")
            sb.appendLine("------------------------------")
            order.items.forEach { item ->
                sb.appendLine(item.deskripsi ?: item.productId ?: "-")
                sb.appendLine("  ${item.quantity} x Rp ${harga.format(item.price)}")
            }
            sb.appendLine("------------------------------")
            sb.appendLine("Subtotal    : Rp ${harga.format(order.subtotal)}")
            if (!order.metodeBayar.isNullOrBlank()) {
                sb.appendLine("Metode Bayar: ${order.metodeBayar}")
            }
            order.nominalDiterima?.let { sb.appendLine("Diterima    : Rp ${harga.format(it)}") }
            order.kembalian?.let { sb.appendLine("Kembalian   : Rp ${harga.format(it)}") }
            sb.appendLine("------------------------------")
            sb.appendLine("Terima kasih")

            Result.success(sb.toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
