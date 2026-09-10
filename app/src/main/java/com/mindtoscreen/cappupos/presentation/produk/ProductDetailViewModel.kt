package com.mindtoscreen.cappupos.presentation.produk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import com.mindtoscreen.cappupos.domain.usecase.HapusProdukUseCase
import com.mindtoscreen.cappupos.domain.usecase.UbahProdukUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk detail produk (lihat + ubah + hapus).
 * FR-01.2: Sistem harus memungkinkan pengguna mengubah dan menghapus produk.
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val ubahProdukUseCase: UbahProdukUseCase,
    private val hapusProdukUseCase: HapusProdukUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    private val _kategoriList = MutableStateFlow<List<Kategori>>(emptyList())
    val kategoriList: StateFlow<List<Kategori>> = _kategoriList.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }

    /**
     * Kategori dinamis dari DB (TASK-004), menggantikan KategoriConstants.
     * TASK-008.
     */
    private fun loadKategori() {
        viewModelScope.launch {
            try {
                _kategoriList.value = categoryRepository.getKategories()
            } catch (e: Exception) {
                // Silent fail untuk kategori (pola sama dengan HomeViewModel)
            }
        }
    }

    suspend fun ubahProduct(product: Product): Result<Unit> {
        return ubahProdukUseCase.execute(product)
    }

    suspend fun hapusProduct(productId: String): Result<Unit> {
        return hapusProdukUseCase.execute(productId)
    }

    init {
        loadKategori()
    }
}
