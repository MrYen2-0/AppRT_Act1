package com.yuen.encuestasockets.feature.auth.domain.repository

import com.yuen.encuestasockets.feature.auth.domain.model.Usuario

interface AuthRepository {

    suspend fun registro(
        username: String,
        password: String
    ): Result<Usuario>

    suspend fun login(
        username: String,
        password: String
    ): Result<String> // JWT
}