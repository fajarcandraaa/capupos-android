package com.mindtoscreen.cappupos.domain.usecase

import javax.inject.Inject

/**
 * Use case untuk mengecek apakah ada produk di dalam database.
 * FR-14.1: Sistem harus mendeteksi kondisi belum ada produk sama sekali
 */
class CekProdukKosongUseCase @Inject constructor(
    private val productRepo: com.mindtoscreen.cappupos.domain.repository.ProductRepository
) {
    suspend fun execute(): Boolean {
        return productRepo.getProductCount() == 0
    }
}
