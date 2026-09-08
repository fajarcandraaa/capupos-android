package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import javax.inject.Inject

/**
 * Use case untuk menambah kategori baru.
 * FR-02.1: Sistem harus memungkinkan pengguna menambah kategori
 */
class TambahKategoriUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(nama: String): Result<Unit> {
        return try {
            require(nama.isNotBlank()) { "Nama kategori wajib" }
            categoryRepository.insertKategori(Kategori(nama = nama.trim()))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
