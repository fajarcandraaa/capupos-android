package com.mindtoscreen.cappupos.presentation.transaksi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.model.OrderItem
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import com.mindtoscreen.cappupos.domain.usecase.SimpanTransaksiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class TransaksiUiState(
    val kategoriList: List<Kategori> = emptyList(),
    val allProducts: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val selectedKategoriId: String? = null,
    val searchQuery: String = "",
    val keranjang: List<CartItem> = emptyList(),
    val total: Double = 0.0,
    val successMessage: String? = null,
    val error: String? = null
)

/**
 * ViewModel layar transaksi: pilih produk by kategori/search ke keranjang,
 * atur kuantitas, lalu Simpan/Open Bill sebagai "Belum Bayar".
 * FR-04, FR-07.
 */
@HiltViewModel
class TransaksiViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val simpanTransaksiUseCase: SimpanTransaksiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransaksiUiState())
    val uiState: StateFlow<TransaksiUiState> = _uiState.asStateFlow()

    init {
        loadKategori()
        loadProducts()
    }

    fun loadKategori() {
        viewModelScope.launch {
            try {
                val kategori = categoryRepository.getKategories()
                _uiState.update { it.copy(kategoriList = kategori) }
            } catch (e: Exception) {
                // silent fail kategori
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            val all = productRepository.getProducts()
            _uiState.update { state ->
                state.copy(
                    allProducts = all,
                    filteredProducts = applyFilter(all, state.selectedKategoriId, state.searchQuery)
                )
            }
        }
    }

    fun selectKategori(kategoriId: String?) {
        _uiState.update { state ->
            state.copy(
                selectedKategoriId = kategoriId,
                filteredProducts = applyFilter(state.allProducts, kategoriId, state.searchQuery)
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                filteredProducts = applyFilter(state.allProducts, state.selectedKategoriId, query)
            )
        }
    }

    private fun applyFilter(
        products: List<Product>,
        kategoriId: String?,
        query: String
    ): List<Product> {
        var result = products
        if (kategoriId != null) {
            result = result.filter { it.kategoriId == kategoriId }
        }
        if (query.isNotBlank()) {
            result = result.filter { it.nama.contains(query, ignoreCase = true) }
        }
        return result
    }

    fun tambahKeKeranjang(product: Product) {
        _uiState.update { state ->
            val existing = state.keranjang.find { it.product.id == product.id }
            val keranjang = if (existing != null) {
                state.keranjang.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                state.keranjang + CartItem(product = product, quantity = 1)
            }
            state.copy(keranjang = keranjang, total = hitungTotal(keranjang))
        }
    }

    fun tambahQty(productId: String?) {
        _uiState.update { state ->
            val keranjang = state.keranjang.map {
                if (it.product.id == productId) it.copy(quantity = it.quantity + 1) else it
            }
            state.copy(keranjang = keranjang, total = hitungTotal(keranjang))
        }
    }

    fun kurangQty(productId: String?) {
        _uiState.update { state ->
            val keranjang = state.keranjang.mapNotNull {
                when {
                    it.product.id != productId -> it
                    it.quantity <= 1 -> null
                    else -> it.copy(quantity = it.quantity - 1)
                }
            }
            state.copy(keranjang = keranjang, total = hitungTotal(keranjang))
        }
    }

    private fun hitungTotal(keranjang: List<CartItem>): Double {
        return keranjang.sumOf { it.product.harga * it.quantity }
    }

    fun simpanBill() {
        val items = _uiState.value.keranjang.map {
            OrderItem(
                productId = it.product.id,
                namaItem = it.product.nama.ifBlank { null },
                quantity = it.quantity,
                price = it.product.harga
            )
        }
        if (items.isEmpty()) {
            _uiState.update { it.copy(error = "Keranjang masih kosong") }
            return
        }
        viewModelScope.launch {
            simpanTransaksiUseCase.execute(Order(items = items)).onSuccess {
                _uiState.update { state ->
                    state.copy(keranjang = emptyList(), total = 0.0, successMessage = "Transaksi tersimpan sebagai Belum Bayar")
                }
            }.onFailure { e ->
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, error = null) }
    }
}
