package com.mindtoscreen.cappupos.presentation.profilusaha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Store
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import com.mindtoscreen.cappupos.domain.repository.StoreRepository
import com.mindtoscreen.cappupos.domain.usecase.UbahDataUsahaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilUsahaViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val orderRepository: OrderRepository,
    private val ubahDataUsahaUseCase: UbahDataUsahaUseCase
) : ViewModel() {

    private val _store = MutableStateFlow<Store?>(null)
    val store: StateFlow<Store?> = _store.asStateFlow()

    private val _lastLunasOrderId = MutableStateFlow<String?>(null)
    val lastLunasOrderId: StateFlow<String?> = _lastLunasOrderId.asStateFlow()

    init {
        loadStore()
        loadLastLunasOrder()
    }

    private fun loadStore() {
        viewModelScope.launch {
            _store.value = storeRepository.getStore()
        }
    }

    /** Struk hanya reachable dari sini (presentation/riwayat di luar allowed_paths TASK-007). */
    private fun loadLastLunasOrder() {
        viewModelScope.launch {
            _lastLunasOrderId.value = orderRepository.getAllOrders()
                .filter { it.status == "lunas" }
                .maxByOrNull { it.tanggal }
                ?.id
        }
    }

    suspend fun simpan(store: Store): Result<Unit> {
        return ubahDataUsahaUseCase.execute(store)
    }
}
