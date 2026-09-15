package com.mindtoscreen.cappupos.presentation.laporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.StokHistoriItem
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LaporanStokUiState(
    val items: List<StokHistoriItem> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel histori perubahan stok (dari pembayaran transaksi). FR-09.
 */
@HiltViewModel
class LaporanStokViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LaporanStokUiState())
    val uiState: StateFlow<LaporanStokUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            runCatching { orderRepository.getStokHistori() }
                .onSuccess { items -> _uiState.update { it.copy(items = items, loading = false) } }
                .onFailure { e -> _uiState.update { it.copy(loading = false, error = e.message) } }
        }
    }
}
