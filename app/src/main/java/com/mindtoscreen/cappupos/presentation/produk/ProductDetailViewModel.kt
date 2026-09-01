package com.mindtoscreen.cappupos.presentation.produk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Product
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
    private val ubahProdukUseCase: UbahProdukUseCase,
    private val hapusProdukUseCase: HapusProdukUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }

    suspend fun ubahProduct(product: Product): Result<Unit> {
        return ubahProdukUseCase.execute(product)
    }

    suspend fun hapusProduct(productId: String): Result<Unit> {
        return hapusProdukUseCase.execute(productId)
    }
}
