package com.yuen.encuestasockets.feature.auth.domain.usecase

import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.repository.AuthRepository

class RegistroUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(username: String): Result<Usuario> {
        if (username.isBlank()) {
            return Result.failure(Exception("El nombre de usuario no puede estar vacío"))
        }

        if (username.length < 3) {
            return Result.failure(Exception("El nombre de usuario debe tener al menos 3 caracteres"))
        }

        if (username.length > 50) {
            return Result.failure(Exception("El nombre de usuario no puede tener más de 50 caracteres"))
        }

        if (!username.matches(Regex("^[a-zA-Z0-9_-]+$"))) {
            return Result.failure(Exception("El nombre de usuario solo puede contener letras, números, guiones y guiones bajos"))
        }

        return repository.registro(username)
    }
}