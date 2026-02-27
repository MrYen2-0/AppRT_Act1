package com.yuen.encuestasockets.feature.encuestas.domain.repository

import com.yuen.encuestasockets.feature.encuestas.domain.entities.Encuesta
import kotlinx.coroutines.flow.Flow

interface EncuestasRepository {
    suspend fun getEncuestas(): Result<List<Encuesta>>
    suspend fun getEncuesta(preguntaId: Int): Result<Encuesta>
    suspend fun crearEncuesta(titulo: String, opciones: List<String>): Result<Encuesta>
    suspend fun votar(opcionId: Int, usuarioId: Int): Result<Unit>
    suspend fun eliminarEncuesta(preguntaId: Int): Result<Unit>
    suspend fun actualizarEncuesta(preguntaId: Int, titulo: String, opciones: List<String>): Result<Encuesta>
    fun observarVotosEnVivo(): Flow<Pair<Int, Int>>
}
