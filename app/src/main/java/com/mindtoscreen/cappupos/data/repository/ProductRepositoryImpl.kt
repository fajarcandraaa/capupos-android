package com.mindtoscreen.cappupos.data.repository

import com.mindtoscreen.cappupos.data.dao.ProductDao
import com.mindtoscreen.cappupos.data.entities.ProductEntity
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productDao.getActiveProducts().map { it.toDomain() }
    }

    override suspend fun getProductById(productId: String): Product? {
        return productDao.getById(productId)?.toDomain()
    }

    override suspend fun getProductCount(): Int {
        return productDao.countActiveProducts()
    }

    override suspend fun insertProduct(product: Product) {
        productDao.insert(product.toEntity())
    }

    override suspend fun deleteProduct(productId: String) {
        productDao.softDelete(productId, System.currentTimeMillis())
    }

    private fun ProductEntity.toDomain(): Product {
        return Product(
            id = this.id,
            nama = this.nama,
            foto = this.foto,
            kategoriId = this.kategoriId,
            harga = this.harga,
            deskripsi = this.deskripsi,
            lacakStok = this.lacakStok,
            jumlahStok = this.jumlahStok,
            stokMinimal = this.stokMinimal
        )
    }

    private fun Product.toEntity(): ProductEntity {
        return ProductEntity(
            id = this.id ?: java.util.UUID.randomUUID().toString(),
            nama = this.nama,
            foto = this.foto,
            kategoriId = this.kategoriId,
            harga = this.harga,
            deskripsi = this.deskripsi,
            lacakStok = this.lacakStok,
            jumlahStok = this.jumlahStok,
            stokMinimal = this.stokMinimal,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    }
}
