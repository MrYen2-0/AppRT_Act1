package com.yuen.encuestasockets.feature.encuestas.domain.entities

data class Encuesta(
    val id: Int,
    val titulo: String,
    val opciones: List<OpcionEncuesta>,
    val creador: String,
    val timestamp: String
)

data class OpcionEncuesta(
    val id: Int,
    val texto: String,
    val votos: Int,
    val votantes: List<String> = emptyList()
)