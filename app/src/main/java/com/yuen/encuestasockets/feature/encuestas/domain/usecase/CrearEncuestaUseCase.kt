package com.yuen.encuestasockets.feature.encuestas.domain.usecase

import com.yuen.encuestasockets.feature.encuestas.domain.entities.Encuesta
import com.yuen.encuestasockets.feature.encuestas.domain.repository.EncuestasRepository
import javax.inject.Inject

class CrearEncuestaUseCase @Inject constructor(
    private val repository: EncuestasRepository
) {
    suspend operator fun invoke(titulo: String, opciones: List<String>): Result<Encuesta> {
        if (titulo.isBlank()) {
            return Result.failure(Exception("El título no puede estar vacío"))
        }
        if (opciones.size < 2) {
            return Result.failure(Exception("Debe haber al menos 2 opciones"))
        }
        if (opciones.any { it.isBlank() }) {
            return Result.failure(Exception("Todas las opciones deben tener texto"))
        }
        return repository.crearEncuesta(titulo, opciones)
    }
}
