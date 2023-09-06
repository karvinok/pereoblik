package com.vilinesoft.home

import androidx.lifecycle.ViewModel
import com.vilinesoft.domain.model.Good
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
    val uiState: StateFlow<State> get() = _uiState.asStateFlow()

    fun onDialogDismiss() {
        _uiState.value = uiState.value.copy(
            isDialogShowing = false
        )
    }

    data class State(
        val isDialogShowing: Boolean = false,
        val good: Good? = null
    )
}