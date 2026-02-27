package com.yuen.encuestasockets.feature.encuestas.domain.usecase

import com.yuen.encuestasockets.feature.encuestas.domain.entities.Encuesta
import com.yuen.encuestasockets.feature.encuestas.domain.repository.EncuestasRepository
import javax.inject.Inject

class GetEncuestasUseCase @Inject constructor(
    private val repository: EncuestasRepository
) {
    suspend operator fun invoke(): Result<List<Encuesta>> {
        return repository.getEncuestas()
    }
}
