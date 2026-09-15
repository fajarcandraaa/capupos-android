package com.mindtoscreen.cappupos.presentation.riwayat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.FilterRiwayat
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import com.mindtoscreen.cappupos.domain.usecase.HapusTransaksiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class RiwayatGroup(
    val tanggal: String,
    val orders: List<Order>
)

data class RiwayatUiState(
    val allOrders: List<Order> = emptyList(),
    val groups: List<RiwayatGroup> = emptyList(),
    val kategoriList: List<Kategori> = emptyList(),
    val filter: FilterRiwayat = FilterRiwayat(),
    val loading: Boolean = true,
    val successMessage: String? = null,
    val error: String? = null
)

/**
 * ViewModel riwayat transaksi (belum bayar + lunas) dengan filter.
 * Filter (kategori/tanggal/metode) dieksekusi in-memory di ViewModel
 * per DECISIONS.md [2026-09-13] poin 8 — kategori via OrderDetail→Product→kategoriId.
 */
@HiltViewModel
class RiwayatViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val hapusTransaksiUseCase: HapusTransaksiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RiwayatUiState())
    val uiState: StateFlow<RiwayatUiState> = _uiState.asStateFlow()

    private var filterKategoriId: String? = null
    private var filterTanggalAwal: Long? = null
    private var filterTanggalAkhir: Long? = null
    private var filterMetodeBayar: String? = null

    // productId -> kategoriId, dipetakan sekali saat load (in-memory join).
    private var produkKategori: Map<String, String?> = emptyMap()

    init {
        loadRiwayat()
    }

    fun loadRiwayat() {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getAllOrders()
                val kategoriList = categoryRepository.getKategories()
                produkKategori = productRepository.getProducts()
                    .mapNotNull { p -> p.id?.let { it to p.kategoriId } }
                    .toMap()
                _uiState.update {
                    it.copy(
                        allOrders = orders,
                        kategoriList = kategoriList,
                        loading = false,
                        groups = applyFilter(orders)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    fun setFilterKategori(kategoriId: String?) {
        filterKategoriId = kategoriId
        applyFilterToState()
    }

    fun setFilterMetode(metodeBayar: String?) {
        filterMetodeBayar = metodeBayar
        applyFilterToState()
    }

    fun setFilterTanggal(awal: Long?, akhir: Long?) {
        filterTanggalAwal = awal
        filterTanggalAkhir = akhir
        applyFilterToState()
    }

    fun resetFilter() {
        filterKategoriId = null
        filterTanggalAwal = null
        filterTanggalAkhir = null
        filterMetodeBayar = null
        applyFilterToState()
    }

    fun hapusTransaksi(orderId: String) {
        viewModelScope.launch {
            hapusTransaksiUseCase.execute(orderId)
                .onSuccess {
                    loadRiwayat()
                    _uiState.update { it.copy(successMessage = "Transaksi dihapus") }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, error = null) }
    }

    private fun applyFilterToState() {
        _uiState.update { it.copy(groups = applyFilter(it.allOrders)) }
    }

    private fun applyFilter(orders: List<Order>): List<RiwayatGroup> {
        val fmt = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))

        val filtered = orders.filter { order ->
            val cocokTanggal = (filterTanggalAwal == null || order.tanggal >= filterTanggalAwal!!) &&
                (filterTanggalAkhir == null || order.tanggal <= filterTanggalAkhir!!)
            val cocokMetode = filterMetodeBayar == null || order.metodeBayar == filterMetodeBayar
            val cocokKategori = filterKategoriId == null || order.items.any { item ->
                val kategoriIdProduk = item.productId?.let { produkKategori[it] }
                // Item manual (tanpa productId) lolos hanya bila tidak ada filter kategori.
                kategoriIdProduk != null && kategoriIdProduk == filterKategoriId
            }
            cocokTanggal && cocokMetode && cocokKategori
        }

        return filtered
            .sortedByDescending { it.tanggal }
            .groupBy { fmt.format(Date(it.tanggal)) }
            .map { (tanggal, list) -> RiwayatGroup(tanggal, list) }
    }
}
