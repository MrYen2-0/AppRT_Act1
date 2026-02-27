package com.yuen.encuestasockets.feature.encuestas.domain.entities

data class Encuesta(
    val id: Int,
    val titulo: String,
    val opciones: List<OpcionEncuesta>,
    val creadoEn: String
)

data class OpcionEncuesta(
    val id: Int,
    val texto: String,
    val votos: Int
)
