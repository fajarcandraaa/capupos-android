package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import javax.inject.Inject

/**
 * Use case untuk mengubah nama kategori.
 * FR-02.2: Sistem harus memungkinkan pengguna mengubah nama kategori
 */
class UbahKategoriUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(kategori: Kategori): Result<Unit> {
        return try {
            require(!kategori.id.isNullOrEmpty()) { "ID kategori wajib" }
            require(kategori.nama.isNotBlank()) { "Nama kategori wajib" }
            categoryRepository.updateKategori(kategori)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
