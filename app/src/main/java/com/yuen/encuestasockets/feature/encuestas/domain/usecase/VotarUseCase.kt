package com.yuen.encuestasockets.feature.encuestas.domain.usecase

import com.yuen.encuestasockets.feature.encuestas.domain.repository.EncuestasRepository
import javax.inject.Inject

class VotarUseCase @Inject constructor(
    private val repository: EncuestasRepository
) {
    suspend operator fun invoke(opcionId: Int, usuarioId: Int): Result<Unit> {
        return repository.votar(opcionId, usuarioId)
    }
}
