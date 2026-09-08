package com.mindtoscreen.cappupos.presentation.kategori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.Kategori
import com.mindtoscreen.cappupos.domain.repository.CategoryRepository
import com.mindtoscreen.cappupos.domain.usecase.HapusKategoriUseCase
import com.mindtoscreen.cappupos.domain.usecase.ReorderKategoriUseCase
import com.mindtoscreen.cappupos.domain.usecase.TambahKategoriUseCase
import com.mindtoscreen.cappupos.domain.usecase.UbahKategoriUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KategoriListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val tambahKategoriUseCase: TambahKategoriUseCase,
    private val ubahKategoriUseCase: UbahKategoriUseCase,
    private val hapusKategoriUseCase: HapusKategoriUseCase,
    private val reorderKategoriUseCase: ReorderKategoriUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(KategoriListUiState())
    val uiState: StateFlow<KategoriListUiState> = _uiState.asStateFlow()

    init {
        loadKategori()
    }

    fun loadKategori() {
        viewModelScope.launch {
            try {
                val kategori = categoryRepository.getKategories()
                _uiState.update { it.copy(kategori = kategori, loading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    fun tambahKategori(nama: String) {
        viewModelScope.launch {
            tambahKategoriUseCase.execute(nama).onSuccess {
                loadKategori()
                _uiState.update { it.copy(successMessage = "Kategori berhasil ditambahkan") }
            }.onFailure {
                _uiState.update { it.copy(error = it.message) }
            }
        }
    }

    fun ubahKategori(kategori: Kategori) {
        viewModelScope.launch {
            ubahKategoriUseCase.execute(kategori).onSuccess {
                loadKategori()
                _uiState.update { it.copy(successMessage = "Kategori berhasil diubah") }
            }.onFailure {
                _uiState.update { it.copy(error = it.message) }
            }
        }
    }

    fun hapusKategori(kategoriId: String) {
        viewModelScope.launch {
            hapusKategoriUseCase.execute(kategoriId).onSuccess {
                loadKategori()
                _uiState.update { it.copy(successMessage = "Kategori berhasil dihapus") }
            }.onFailure {
                _uiState.update { it.copy(error = it.message) }
            }
        }
    }

    fun reorderKategori(orderedIds: List<String>) {
        viewModelScope.launch {
            reorderKategoriUseCase.execute(orderedIds).onSuccess {
                loadKategori()
            }.onFailure {
                _uiState.update { it.copy(error = it.message) }
            }
        }
    }

    suspend fun countProductsByKategori(kategoriId: String): Int {
        return categoryRepository.countProductsByKategori(kategoriId)
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, error = null) }
    }
}

data class KategoriListUiState(
    val kategori: List<Kategori> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null,
    val successMessage: String? = null
)
