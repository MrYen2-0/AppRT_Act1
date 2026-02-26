package com.yuen.encuestasockets.feature.auth.domain.usecase

import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(username: String): Result<Usuario> {
        if (username.isBlank()) {
            return Result.failure(Exception("El nombre de usuario no puede estar vacío"))
        }

        return repository.login(username)
    }
}