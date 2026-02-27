package com.yuen.encuestasockets.feature.auth.data.remote
import com.yuen.encuestasockets.feature.auth.data.remote.dto.UsuarioResponse
import com.yuen.encuestasockets.feature.auth.data.remote.dto.TokenResponse
import com.yuen.encuestasockets.feature.auth.data.remote.dto.AuthRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class AuthApi @Inject constructor(
    private val client: HttpClient
) {

    companion object {
        private const val BASE_URL = "http://54.226.172.181:8000"
    }

    suspend fun registro(
        username: String,
        password: String
    ): UsuarioResponse {

        return client.post("$BASE_URL/auth/registro") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(username, password))
        }.body()
    }

    suspend fun login(
        username: String,
        password: String
    ): TokenResponse {

        return client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(username, password))
        }.body()
    }
}