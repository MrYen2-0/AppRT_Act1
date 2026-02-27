package com.yuen.encuestasockets.feature.encuestas.data.remote

import com.yuen.encuestasockets.feature.encuestas.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject

class EncuestasApi @Inject constructor(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "http://54.226.172.181:8000"
    }

    suspend fun getEncuestas(): Result<List<PreguntaResponse>> {
        return try {
            val response: HttpResponse = client.get("$BASE_URL/encuestas/")
            when (response.status) {
                HttpStatusCode.OK -> Result.success(response.body<List<PreguntaResponse>>())
                else -> Result.failure(Exception("Error al obtener encuestas: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEncuesta(preguntaId: Int): Result<PreguntaResponse> {
        return try {
            val response: HttpResponse = client.get("$BASE_URL/encuestas/$preguntaId")
            when (response.status) {
                HttpStatusCode.OK -> Result.success(response.body<PreguntaResponse>())
                else -> Result.failure(Exception("Error al obtener encuesta: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun crearEncuesta(titulo: String, opciones: List<String>): Result<PreguntaResponse> {
        return try {
            val response: HttpResponse = client.post("$BASE_URL/encuestas/") {
                contentType(ContentType.Application.Json)
                setBody(PreguntaCreate(titulo = titulo, opciones = opciones))
            }
            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> {
                    Result.success(response.body<PreguntaResponse>())
                }
                else -> Result.failure(Exception("Error al crear encuesta: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun votar(opcionId: Int, usuarioId: Int): Result<Unit> {
        return try {
            val response: HttpResponse = client.post("$BASE_URL/encuestas/votar") {
                contentType(ContentType.Application.Json)
                setBody(VotoCreate(opcionId = opcionId, usuarioId = usuarioId))
            }
            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> Result.success(Unit)
                else -> Result.failure(Exception("Error al votar: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarEncuesta(preguntaId: Int): Result<Unit> {
        return try {
            val response: HttpResponse = client.delete("$BASE_URL/encuestas/$preguntaId")
            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.NoContent -> Result.success(Unit)
                else -> Result.failure(Exception("Error al eliminar encuesta: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarEncuesta(preguntaId: Int, titulo: String, opciones: List<String>): Result<PreguntaResponse> {
        return try {
            val response: HttpResponse = client.put("$BASE_URL/encuestas/$preguntaId") {
                contentType(ContentType.Application.Json)
                setBody(PreguntaCreate(titulo = titulo, opciones = opciones))
            }
            when (response.status) {
                HttpStatusCode.OK -> Result.success(response.body<PreguntaResponse>())
                else -> Result.failure(Exception("Error al actualizar encuesta: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
