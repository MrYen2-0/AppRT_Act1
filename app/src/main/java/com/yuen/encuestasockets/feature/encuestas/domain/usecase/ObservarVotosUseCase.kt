package com.yuen.encuestasockets.feature.encuestas.domain.usecase

import com.yuen.encuestasockets.feature.encuestas.domain.repository.EncuestasRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservarVotosUseCase @Inject constructor(
    private val repository: EncuestasRepository
) {
    operator fun invoke(): Flow<Pair<Int, Int>> {
        return repository.observarVotosEnVivo()
    }
}
