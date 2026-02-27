package com.yuen.encuestasockets.feature.auth.domain.usecase

import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<String> {

        if (username.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Campos vacíos"))
        }

        return repository.login(username, password)
    }
}