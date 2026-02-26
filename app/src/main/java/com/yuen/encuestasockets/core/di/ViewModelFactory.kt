package com.yuen.encuestasockets.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuen.encuestasockets.core.network.HttpClientFactory
import com.yuen.encuestasockets.feature.auth.data.remote.AuthApi
import com.yuen.encuestasockets.feature.auth.data.repository.AuthRepositoryImpl
import com.yuen.encuestasockets.feature.auth.domain.usecase.LoginUseCase
import com.yuen.encuestasockets.feature.auth.domain.usecase.RegistroUseCase
import com.yuen.encuestasockets.feature.auth.presentation.viewmodel.AuthViewModel
import com.yuen.encuestasockets.feature.encuestas.presentation.viewmodel.EncuestasViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    private val httpClient = HttpClientFactory.create()

    private val authApi = AuthApi(httpClient)
    private val authRepository = AuthRepositoryImpl(authApi)
    private val registroUseCase = RegistroUseCase(authRepository)
    private val loginUseCase = LoginUseCase(authRepository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(registroUseCase, loginUseCase) as T
            }
            modelClass.isAssignableFrom(EncuestasViewModel::class.java) -> {
                EncuestasViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}