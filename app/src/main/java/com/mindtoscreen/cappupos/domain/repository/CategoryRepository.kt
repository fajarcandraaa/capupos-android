package com.mindtoscreen.cappupos.domain.repository

import com.mindtoscreen.cappupos.domain.model.Kategori

interface CategoryRepository {
    suspend fun getKategories(): List<Kategori>
    suspend fun getKategoriById(kategoriId: String): Kategori?
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(kategori: Kategori)
    suspend fun deleteKategori(kategoriId: String): Int
    suspend fun reorderKategori(orderedIds: List<String>)
    suspend fun countProductsByKategori(kategoriId: String): Int
    suspend fun detachProductsFromKategori(kategoriId: String)
}
