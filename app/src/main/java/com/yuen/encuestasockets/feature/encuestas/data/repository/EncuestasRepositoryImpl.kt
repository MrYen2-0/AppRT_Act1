package com.yuen.encuestasockets.feature.encuestas.data.repository

import com.yuen.encuestasockets.feature.encuestas.data.remote.EncuestasApi
import com.yuen.encuestasockets.feature.encuestas.data.remote.EncuestasWebSocket
import com.yuen.encuestasockets.feature.encuestas.data.remote.dto.PreguntaResponse
import com.yuen.encuestasockets.feature.encuestas.domain.entities.Encuesta
import com.yuen.encuestasockets.feature.encuestas.domain.entities.OpcionEncuesta
import com.yuen.encuestasockets.feature.encuestas.domain.repository.EncuestasRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EncuestasRepositoryImpl @Inject constructor(
    private val api: EncuestasApi,
    private val webSocket: EncuestasWebSocket
) : EncuestasRepository {

    override suspend fun getEncuestas(): Result<List<Encuesta>> {
        return api.getEncuestas().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getEncuesta(preguntaId: Int): Result<Encuesta> {
        return api.getEncuesta(preguntaId).map { it.toDomain() }
    }

    override suspend fun crearEncuesta(titulo: String, opciones: List<String>): Result<Encuesta> {
        return api.crearEncuesta(titulo, opciones).map { it.toDomain() }
    }

    override suspend fun votar(opcionId: Int, usuarioId: Int): Result<Unit> {
        return api.votar(opcionId, usuarioId)
    }

    override suspend fun eliminarEncuesta(preguntaId: Int): Result<Unit> {
        return api.eliminarEncuesta(preguntaId)
    }

    override suspend fun actualizarEncuesta(preguntaId: Int, titulo: String, opciones: List<String>): Result<Encuesta> {
        return api.actualizarEncuesta(preguntaId, titulo, opciones).map { it.toDomain() }
    }

    override fun observarVotosEnVivo(): Flow<Pair<Int, Int>> {
        return webSocket.observarVotos()
    }
}

private fun PreguntaResponse.toDomain(): Encuesta {
    return Encuesta(
        id = id,
        titulo = titulo,
        opciones = opciones.map { opcion ->
            OpcionEncuesta(
                id = opcion.id,
                texto = opcion.texto,
                votos = opcion.votos
            )
        },
        creadoEn = creadoEn
    )
}
