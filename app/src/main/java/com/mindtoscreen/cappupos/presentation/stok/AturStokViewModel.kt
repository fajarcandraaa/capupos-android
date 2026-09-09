package com.mindtoscreen.cappupos.presentation.stok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AturStokViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AturStokUiState())
    val uiState: StateFlow<AturStokUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(productId)
                _uiState.update {
                    it.copy(
                        product = product,
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    fun updateStok(
        lacakStok: Boolean,
        jumlahStok: Int?,
        stokMinimal: Int?
    ) {
        val product = _uiState.value.product ?: return
        viewModelScope.launch {
            try {
                val updated = product.copy(
                    lacakStok = lacakStok,
                    jumlahStok = if (lacakStok) jumlahStok else null,
                    stokMinimal = if (lacakStok) stokMinimal else null
                )
                productRepository.insertProduct(updated)
                _uiState.update { it.copy(successMessage = "Stok berhasil diperbarui") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, error = null) }
    }
}

data class AturStokUiState(
    val product: Product? = null,
    val loading: Boolean = true,
    val error: String? = null,
    val successMessage: String? = null
)
