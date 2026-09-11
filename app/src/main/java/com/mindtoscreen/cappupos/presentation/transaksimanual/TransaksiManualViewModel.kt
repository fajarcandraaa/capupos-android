package com.mindtoscreen.cappupos.presentation.transaksimanual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.model.OrderItem
import com.mindtoscreen.cappupos.domain.usecase.SimpanTransaksiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManualItem(
    val nominal: String,
    val deskripsi: String
)

data class TransaksiManualUiState(
    val items: List<ManualItem> = listOf(ManualItem("", "")),
    val loading: Boolean = false,
    val successMessage: String? = null,
    val error: String? = null
)

/**
 * ViewModel transaksi manual: input nominal + deskripsi bebas per item,
 * simpan sebagai "Belum Bayar" (FR-04).
 */
@HiltViewModel
class TransaksiManualViewModel @Inject constructor(
    private val simpanTransaksiUseCase: SimpanTransaksiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransaksiManualUiState())
    val uiState: StateFlow<TransaksiManualUiState> = _uiState.asStateFlow()

    fun addItem() {
        _uiState.update { it.copy(items = it.items + ManualItem("", "")) }
    }

    fun updateNominal(index: Int, nominal: String) {
        _uiState.update { state ->
            val items = state.items.mapIndexed { i, item ->
                if (i == index) item.copy(nominal = nominal) else item
            }
            state.copy(items = items)
        }
    }

    fun updateDeskripsi(index: Int, deskripsi: String) {
        _uiState.update { state ->
            val items = state.items.mapIndexed { i, item ->
                if (i == index) item.copy(deskripsi = deskripsi) else item
            }
            state.copy(items = items)
        }
    }

    fun removeItem(index: Int) {
        _uiState.update { state ->
            if (state.items.size <= 1) state
            else state.copy(items = state.items.filterIndexed { i, _ -> i != index })
        }
    }

    fun simpan() {
        val orderItems = mutableListOf<OrderItem>()
        _uiState.value.items.forEach { item ->
            val nominal = item.nominal.trim().toDoubleOrNull()
            if (nominal == null) {
                _uiState.update { it.copy(error = "Nominal wajib diisi") }
                return
            }
            orderItems.add(
                OrderItem(
                    productId = null,
                    deskripsi = item.deskripsi.trim().ifEmpty { null },
                    quantity = 1,
                    price = nominal
                )
            )
        }
        if (orderItems.isEmpty()) {
            _uiState.update { it.copy(error = "Keranjang masih kosong") }
            return
        }
        viewModelScope.launch {
            simpanTransaksiUseCase.execute(Order(items = orderItems)).onSuccess {
                _uiState.update { it.copy(successMessage = "Transaksi tersimpan sebagai Belum Bayar") }
            }.onFailure { e ->
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, error = null) }
    }
}
