package com.mindtoscreen.cappupos.presentation.struk

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindtoscreen.cappupos.domain.usecase.GenerateStrukUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StrukViewModel @Inject constructor(
    private val generateStrukUseCase: GenerateStrukUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderId: String = savedStateHandle["orderId"] ?: ""

    private val _strukText = MutableStateFlow<String?>(null)
    val strukText: StateFlow<String?> = _strukText.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadStruk()
    }

    private fun loadStruk() {
        viewModelScope.launch {
            generateStrukUseCase.execute(orderId)
                .onSuccess { _strukText.value = it }
                .onFailure { _error.value = it.message }
        }
    }
}
