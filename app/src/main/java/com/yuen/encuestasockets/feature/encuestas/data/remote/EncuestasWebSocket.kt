package com.yuen.encuestasockets.feature.encuestas.data.remote

import com.yuen.encuestasockets.feature.encuestas.data.remote.dto.VotoWebSocketMessage
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class EncuestasWebSocket @Inject constructor(
    private val client: HttpClient
) {
    companion object {
        private const val WS_HOST = "54.226.172.181"
        private const val WS_PORT = 8000
        private const val WS_PATH = "/ws/encuestas"
    }

    private val json = Json { ignoreUnknownKeys = true }

    fun observarVotos(): Flow<Pair<Int, Int>> = flow {
        try {
            client.webSocket(host = WS_HOST, port = WS_PORT, path = WS_PATH) {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        try {
                            val message = json.decodeFromString<VotoWebSocketMessage>(text)
                            if (message.type == "voto") {
                                emit(Pair(message.data.opcionId, message.data.usuarioId))
                            }
                        } catch (_: Exception) {
                            // Error de parsing, continuar escuchando
                        }
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
