package com.yuen.encuestasockets.feature.encuestas.domain.usecase

import com.yuen.encuestasockets.feature.encuestas.domain.entities.Encuesta
import com.yuen.encuestasockets.feature.encuestas.domain.repository.EncuestasRepository
import javax.inject.Inject

class GetEncuestaUseCase @Inject constructor(
    private val repository: EncuestasRepository
) {
    suspend operator fun invoke(preguntaId: Int): Result<Encuesta> {
        return repository.getEncuesta(preguntaId)
    }
}
