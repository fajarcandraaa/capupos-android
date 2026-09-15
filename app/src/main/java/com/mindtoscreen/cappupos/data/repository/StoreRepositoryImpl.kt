package com.mindtoscreen.cappupos.data.repository

import com.mindtoscreen.cappupos.data.dao.StoreDao
import com.mindtoscreen.cappupos.data.entities.StoreEntity
import com.mindtoscreen.cappupos.domain.model.Store
import com.mindtoscreen.cappupos.domain.repository.StoreRepository
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeDao: StoreDao
) : StoreRepository {

    override suspend fun getStore(): Store? {
        return storeDao.getStore()?.toDomain()
    }

    // Row tunggal (id = 1): update bila sudah ada, insert bila belum.
    override suspend fun saveStore(store: Store): Result<Unit> {
        return try {
            val existing = storeDao.getStore()
            if (existing == null) {
                storeDao.insert(store.toEntity())
            } else {
                storeDao.update(store.toEntity())
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun StoreEntity.toDomain(): Store {
        return Store(
            id = this.id,
            nama = this.nama,
            alamat = this.alamat,
            logo = this.logo,
            kategori = this.kategori,
            deskripsi = this.deskripsi,
            telepon = this.telepon
        )
    }

    private fun Store.toEntity(): StoreEntity {
        return StoreEntity(
            id = 1,
            nama = this.nama,
            alamat = this.alamat,
            logo = this.logo,
            kategori = this.kategori,
            deskripsi = this.deskripsi,
            telepon = this.telepon
        )
    }
}
