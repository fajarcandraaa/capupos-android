package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import javax.inject.Inject

/**
 * Use case untuk menghapus kategori.
 * FR-02.3: Sistem harus memungkinkan pengguna menghapus kategori.
 * Produk terkait TIDAK ikut terhapus: kategoriId produk diset null ("Tanpa Kategori").
 */
class HapusKategoriUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(kategoriId: String): Result<Unit> {
        return try {
            require(kategoriId.isNotEmpty()) { "ID kategori wajib" }
            categoryRepository.detachProductsFromKategori(kategoriId)
            categoryRepository.deleteKategori(kategoriId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
