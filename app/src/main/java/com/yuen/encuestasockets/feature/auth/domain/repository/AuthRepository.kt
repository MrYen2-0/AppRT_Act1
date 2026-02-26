package com.yuen.encuestasockets.feature.auth.domain.repository

import com.yuen.encuestasockets.feature.auth.domain.model.Usuario

interface AuthRepository {
    suspend fun registro(username: String): Result<Usuario>
    suspend fun login(username: String): Result<Usuario>
}