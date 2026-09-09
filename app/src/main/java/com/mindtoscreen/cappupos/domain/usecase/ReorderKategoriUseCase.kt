package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import javax.inject.Inject

/**
 * Use case untuk mengubah urutan kategori (persisten via field `urutan`).
 * FR-02.4: Sistem harus memungkinkan pengguna mengurutkan kategori.
 */
class ReorderKategoriUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(orderedIds: List<String>): Result<Unit> {
        return try {
            if (orderedIds.isEmpty()) {
                Result.success(Unit)
            } else {
                categoryRepository.reorderKategori(orderedIds)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
