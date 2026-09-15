package com.mindtoscreen.cappupos.presentation.export

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.usecase.ExportDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor(
    private val exportDataUseCase: ExportDataUseCase
) : ViewModel() {

    private val _exportedFile = MutableStateFlow<File?>(null)
    val exportedFile: StateFlow<File?> = _exportedFile.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun export(outputDir: File) {
        viewModelScope.launch {
            exportDataUseCase.execute(outputDir)
                .onSuccess { _exportedFile.value = it }
                .onFailure { _error.value = it.message }
        }
    }
}
