package com.mindtoscreen.cappupos.data.repository

import com.mindtoscreen.cappupos.data.dao.CategoryDao
import com.mindtoscreen.cappupos.data.dao.ProductDao
import com.mindtoscreen.cappupos.data.entities.CategoryEntity
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao
) : CategoryRepository {
    override suspend fun getKategories(): List<Kategori> {
        return categoryDao.getAll().map { it.toDomain() }
    }

    override suspend fun getKategoriById(kategoriId: String): Kategori? {
        return categoryDao.getById(kategoriId)?.toDomain()
    }

    override suspend fun insertKategori(kategori: Kategori) {
        categoryDao.insert(kategori.toEntity())
    }

    override suspend fun updateKategori(kategori: Kategori) {
        val id = kategori.id ?: return
        categoryDao.update(id, kategori.nama, System.currentTimeMillis())
    }

    override suspend fun deleteKategori(kategoriId: String): Int {
        return categoryDao.delete(kategoriId)
    }

    override suspend fun reorderKategori(orderedIds: List<String>) {
        orderedIds.forEachIndexed { index, id ->
            categoryDao.updateUrutan(id, index)
        }
    }

    override suspend fun countProductsByKategori(kategoriId: String): Int {
        return categoryDao.countProductsByKategori(kategoriId)
    }

    override suspend fun detachProductsFromKategori(kategoriId: String) {
        productDao.detachKategori(kategoriId, System.currentTimeMillis())
    }

    private fun CategoryEntity.toDomain(): Kategori {
        return Kategori(
            id = this.id,
            nama = this.nama,
            urutan = this.urutan
        )
    }

    private fun Kategori.toEntity(): CategoryEntity {
        val now = System.currentTimeMillis()
        return CategoryEntity(
            id = this.id ?: java.util.UUID.randomUUID().toString(),
            nama = this.nama,
            urutan = this.urutan,
            createdAt = now,
            updatedAt = now
        )
    }
}
