package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Use case untuk menghapus produk.
 * FR-01.2: Sistem harus memungkinkan pengguna menghapus produk
 * Soft delete (isDeleted=1) agar riwayat transaksi tidak rusak (SDD 5.2 + FR-08).
 */
class HapusProdukUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun execute(productId: String): Result<Unit> {
        return try {
            require(productId.isNotEmpty()) { "ID produk wajib" }
            productRepository.deleteProduct(productId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
