package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Store
import com.mindtoscreen.cappupos.domain.repository.StoreRepository
import javax.inject.Inject

/**
 * Use case untuk mengubah data profil usaha. FR-10.
 * Field: nama (wajib), logo (opsional), kategori (opsional free text),
 * deskripsi (opsional), alamat (wajib), telepon (opsional).
 */
class UbahDataUsahaUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend fun execute(store: Store): Result<Unit> {
        return try {
            require(store.nama.isNotBlank()) { "Nama usaha wajib diisi" }
            require(store.alamat.isNotBlank()) { "Alamat usaha wajib diisi" }
            storeRepository.saveStore(store)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
