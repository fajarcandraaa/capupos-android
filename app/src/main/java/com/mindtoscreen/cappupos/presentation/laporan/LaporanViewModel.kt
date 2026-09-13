package com.mindtoscreen.cappupos.presentation.laporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.model.LaporanOverview
import com.mindtoscreen.cappupos.domain.usecase.GenerateLaporanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class LaporanUiState(
    val overview: LaporanOverview? = null,
    val loading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel laporan overview + tren penjualan. FR-09.
 * Default periode: bulan ini.
 */
@HiltViewModel
class LaporanViewModel @Inject constructor(
    private val generateLaporanUseCase: GenerateLaporanUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LaporanUiState())
    val uiState: StateFlow<LaporanUiState> = _uiState.asStateFlow()

    init {
        loadBulanIni()
    }

    fun loadHariIni() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        val awal = cal.timeInMillis
        val akhir = System.currentTimeMillis()
        load(awal, akhir)
    }

    fun loadMingguIni() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        val awal = cal.timeInMillis
        val akhir = System.currentTimeMillis()
        load(awal, akhir)
    }

    fun loadBulanIni() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        val awal = cal.timeInMillis
        val akhir = System.currentTimeMillis()
        load(awal, akhir)
    }

    private fun load(awal: Long, akhir: Long) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            generateLaporanUseCase.execute(awal, akhir)
                .onSuccess { overview -> _uiState.update { it.copy(overview = overview, loading = false) } }
                .onFailure { e -> _uiState.update { it.copy(loading = false, error = e.message) } }
        }
    }
}
