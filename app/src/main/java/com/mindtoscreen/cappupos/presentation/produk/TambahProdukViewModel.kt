package com.mindtoscreen.cappupos.presentation.produk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import com.mindtoscreen.cappupos.domain.usecase.SimpanProdukUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk menambah produk.
 * FR-01.1: Sistem harus memungkinkan pengguna menambah produk
 * dengan field: nama (wajib), foto (opsional), kategori (wajib),
 * harga (wajib), deskripsi (opsional).
 */
@HiltViewModel
class TambahProdukViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val simpanProdukUseCase: SimpanProdukUseCase
) : ViewModel() {

    private val _kategoriList = MutableStateFlow<List<Kategori>>(emptyList())
    val kategoriList: StateFlow<List<Kategori>> = _kategoriList.asStateFlow()

    init {
        loadKategori()
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

    suspend fun simpanProduct(product: Product): Result<Unit> {
        return simpanProdukUseCase.execute(product)
    }
}
