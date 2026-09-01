package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Use case untuk mengubah produk yang sudah ada.
 * FR-01.2: Sistem harus memungkinkan pengguna mengubah produk
 * Implementasi: upsert via insertProduct (Room REPLACE on conflict).
 */
class UbahProdukUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun execute(product: Product): Result<Unit> {
        return try {
            require(!product.id.isNullOrEmpty()) { "ID produk wajib diisi untuk ubah" }
            productRepository.insertProduct(product)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
