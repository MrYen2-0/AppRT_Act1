package com.yuen.encuestasockets.feature.encuestas.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreguntaResponse(
    val id: Int,
    val titulo: String,
    @SerialName("creado_en") val creadoEn: String,
    val opciones: List<OpcionResponse>
)

@Serializable
data class OpcionResponse(
    val id: Int,
    val texto: String,
    val votos: Int
)

@Serializable
data class PreguntaCreate(
    val titulo: String,
    val opciones: List<String>
)

@Serializable
data class VotoCreate(
    @SerialName("opcion_id") val opcionId: Int,
    @SerialName("usuario_id") val usuarioId: Int
)

@Serializable
data class VotoWebSocketMessage(
    val type: String,
    val data: VotoData
)

@Serializable
data class VotoData(
    @SerialName("opcion_id") val opcionId: Int,
    @SerialName("usuario_id") val usuarioId: Int
)
