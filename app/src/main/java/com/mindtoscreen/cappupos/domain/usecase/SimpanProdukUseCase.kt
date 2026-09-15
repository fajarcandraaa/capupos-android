package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Use case untuk menyimpan produk baru.
 * FR-01.1: Sistem harus memungkinkan pengguna menambah produk
 */
class SimpanProdukUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun execute(product: Product): Result<Unit> {
        return try {
            productRepository.insertProduct(product)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
