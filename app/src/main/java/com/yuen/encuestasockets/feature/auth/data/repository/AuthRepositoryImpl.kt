package com.yuen.encuestasockets.feature.auth.data.repository

import com.yuen.encuestasockets.feature.auth.data.remote.AuthApi
import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {

    override suspend fun registro(username: String): Result<Usuario> {
        return api.registro(username).map { response ->
            Usuario(
                id = response.usuario.id,
                username = response.usuario.username,
                createdAt = response.usuario.created_at
            )
        }
    }

    override suspend fun login(username: String): Result<Usuario> {
        return api.login(username).map { response ->
            Usuario(
                id = response.usuario.id,
                username = response.usuario.username,
                createdAt = response.usuario.created_at
            )
        }
    }
}