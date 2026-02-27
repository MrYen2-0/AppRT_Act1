package com.yuen.encuestasockets.feature.auth.data.repository

import com.yuen.encuestasockets.feature.auth.domain.model.Usuario
import com.yuen.encuestasockets.feature.auth.domain.repository.AuthRepository
import com.yuen.encuestasockets.feature.auth.data.remote.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun registro(
        username: String,
        password: String
    ): Result<Usuario> {

        return try {

            val response = api.registro(username, password)

            Result.success(
                Usuario(
                    id = response.id,
                    username = response.username,
                    creado_en = response.creado_en
                )
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ): Result<String> {

        return try {

            val response = api.login(username, password)
            Result.success(response.access_token)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}