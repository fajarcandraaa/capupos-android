package com.mindtoscreen.cappupos.presentation

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
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            val all = productRepository.getProducts()
            _uiState.update {
                it.copy(
                    allProducts = all,
                    filteredProducts = applyFilter(all, it.selectedKategoriId, it.searchQuery)
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

    fun selectTab(tab: String) {
        _uiState.update { it.copy(selectedTab = tab) }
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

    suspend fun getProducts(): List<Product> {
        return productRepository.getProducts()
    }
}

data class HomeUiState(
    val allProducts: List<Product> = emptyList(),
    val selectedKategoriId: String? = null,
    val searchQuery: String = "",
    val filteredProducts: List<Product> = emptyList(),
    val selectedTab: String = "produk"
)

