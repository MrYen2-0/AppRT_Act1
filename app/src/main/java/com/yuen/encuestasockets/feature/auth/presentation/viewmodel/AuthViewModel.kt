package com.yuen.encuestasockets.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.usecase.LoginUseCase
import com.yuen.encuestasockets.feature.auth.domain.usecase.RegistroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val usuario: Usuario? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registroUseCase: RegistroUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            error = null
        )
    }

    fun registro(onSuccess: (String, Int) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = registroUseCase(_uiState.value.username.trim())

            result.fold(
                onSuccess = { usuario ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        usuario = usuario,
                        error = null
                    )
                    onSuccess(usuario.username, usuario.id)
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    fun login(onSuccess: (String, Int) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = loginUseCase(_uiState.value.username.trim())

            result.fold(
                onSuccess = { usuario ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        usuario = usuario,
                        error = null
                    )
                    onSuccess(usuario.username, usuario.id)
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}