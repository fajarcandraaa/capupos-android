package com.mindtoscreen.cappupos.presentation.transaksi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import com.mindtoscreen.cappupos.domain.usecase.UbahStatusPOUseCase
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

data class BelumBayarGroup(
    val tanggal: String,
    val orders: List<Order>
)

data class BelumBayarUiState(
    val groups: List<BelumBayarGroup> = emptyList(),
    val loading: Boolean = true,
    val successMessage: String? = null,
    val error: String? = null
)

/**
 * ViewModel list transaksi tertunda ("Belum Bayar").
 * Grouping per tanggal dilakukan di layer ViewModel (bukan DAO).
 * Mendukung ubah status PO (FR-05, FR-05.5).
 */
@HiltViewModel
class BelumBayarViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val ubahStatusPOUseCase: UbahStatusPOUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BelumBayarUiState())
    val uiState: StateFlow<BelumBayarUiState> = _uiState.asStateFlow()

    init {
        loadBelumBayar()
    }

    fun loadBelumBayar() {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getBelumBayar()
                _uiState.update {
                    it.copy(groups = groupByTanggal(orders), loading = false)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    private fun groupByTanggal(orders: List<Order>): List<BelumBayarGroup> {
        val fmt = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))
        return orders
            .groupBy { fmt.format(Date(it.tanggal)) }
            .map { (tanggal, list) -> BelumBayarGroup(tanggal, list) }
    }

    fun ubahStatusPo(orderId: String, statusPoBaru: String) {
        viewModelScope.launch {
            ubahStatusPOUseCase.execute(orderId, statusPoBaru).onSuccess {
                loadBelumBayar()
                _uiState.update { it.copy(successMessage = "Status PO diperbarui") }
            }.onFailure { e ->
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, error = null) }
    }
}
