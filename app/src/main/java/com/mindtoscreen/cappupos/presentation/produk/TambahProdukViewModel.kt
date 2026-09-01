package com.mindtoscreen.cappupos.presentation.produk

import androidx.lifecycle.ViewModel
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.usecase.SimpanProdukUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel untuk menambah produk.
 * FR-01.1: Sistem harus memungkinkan pengguna menambah produk
 * dengan field: nama (wajib), foto (opsional), kategori (wajib),
 * harga (wajib), deskripsi (opsional).
 */
@HiltViewModel
class TambahProdukViewModel @Inject constructor(
    private val simpanProdukUseCase: SimpanProdukUseCase
) : ViewModel() {

    suspend fun simpanProduct(product: Product): Result<Unit> {
        return simpanProdukUseCase.execute(product)
    }
}
