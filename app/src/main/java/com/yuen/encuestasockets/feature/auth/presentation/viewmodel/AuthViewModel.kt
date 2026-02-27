package com.yuen.encuestasockets.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.usecase.LoginUseCase
import com.yuen.encuestasockets.feature.auth.domain.usecase.RegistroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val usuario: Usuario? = null,
    val token: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registroUseCase: RegistroUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username, error = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }

    fun login(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = loginUseCase(
                _uiState.value.username.trim(),
                _uiState.value.password.trim()
            )
            result.fold(
                onSuccess = { token ->
                    _uiState.value = _uiState.value.copy(isLoading = false, token = token)
                    onSuccess(token)
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                }
            )
        }
    }

    fun registro(onSuccess: (String, Int) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = registroUseCase(
                _uiState.value.username.trim(),
                _uiState.value.password.trim()
            )
            result.fold(
                onSuccess = { usuario ->
                    _uiState.value = _uiState.value.copy(isLoading = false, usuario = usuario)
                    onSuccess(usuario.username, usuario.id)
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                }
            )
        }
    }
}