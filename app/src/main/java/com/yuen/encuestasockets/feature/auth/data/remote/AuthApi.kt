package com.yuen.encuestasockets.feature.auth.data.remote

import com.yuen.encuestasockets.feature.auth.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthApi(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000"
    }

    suspend fun registro(username: String): Result<RegistroResponse> {
        return try {
            val response: HttpResponse = client.post("$BASE_URL/api/usuarios/registro") {
                contentType(ContentType.Application.Json)
                setBody(UsuarioRequest(username))
            }

            when (response.status) {
                HttpStatusCode.Created -> {
                    Result.success(response.body<RegistroResponse>())
                }
                HttpStatusCode.BadRequest -> {
                    val error = response.body<ErrorResponse>()
                    Result.failure(Exception(error.detail))
                }
                else -> {
                    Result.failure(Exception("Error desconocido: ${response.status}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String): Result<LoginResponse> {
        return try {
            val response: HttpResponse = client.post("$BASE_URL/api/usuarios/login") {
                contentType(ContentType.Application.Json)
                setBody(UsuarioRequest(username))
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    Result.success(response.body<LoginResponse>())
                }
                HttpStatusCode.NotFound -> {
                    val error = response.body<ErrorResponse>()
                    Result.failure(Exception(error.detail))
                }
                else -> {
                    Result.failure(Exception("Error desconocido: ${response.status}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}