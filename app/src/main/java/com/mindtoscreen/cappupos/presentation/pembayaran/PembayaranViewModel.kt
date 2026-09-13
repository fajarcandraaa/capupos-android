package com.mindtoscreen.cappupos.presentation.pembayaran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import com.mindtoscreen.cappupos.domain.usecase.BayarTransaksiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PembayaranUiState(
    val order: Order? = null,
    val loading: Boolean = true,
    val paid: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel pembayaran transaksi (tunai/non-tunai). FR-06.
 * Kembalian dihitung di UI dari subtotal order (bukan disimpan sampai bayar ditekan).
 */
@HiltViewModel
class PembayaranViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val bayarTransaksiUseCase: BayarTransaksiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PembayaranUiState())
    val uiState: StateFlow<PembayaranUiState> = _uiState.asStateFlow()

    fun loadOrder(orderId: String) {
        viewModelScope.launch {
            val order = orderRepository.getOrderById(orderId)
            _uiState.update { it.copy(order = order, loading = false) }
        }
    }

    fun bayar(orderId: String, metodeBayar: String, nominalDiterima: Double) {
        viewModelScope.launch {
            bayarTransaksiUseCase.execute(orderId, metodeBayar, nominalDiterima)
                .onSuccess { _uiState.update { it.copy(paid = true) } }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
