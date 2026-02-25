package com.yuen.encuestasockets.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthUiState(
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun login(onSuccess: (String) -> Unit) {
        val username = _uiState.value.username.trim()
        if (username.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "El nombre de usuario no puede estar vacío")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        onSuccess(username)

        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}