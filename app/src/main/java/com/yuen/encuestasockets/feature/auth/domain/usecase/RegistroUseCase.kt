package com.yuen.encuestasockets.feature.auth.domain.usecase

import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegistroUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<Usuario> {

        if (username.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Campos vacíos"))
        }

        if (password.length < 4) {
            return Result.failure(Exception("La contraseña es muy corta"))
        }

        return repository.registro(username, password)
    }
}