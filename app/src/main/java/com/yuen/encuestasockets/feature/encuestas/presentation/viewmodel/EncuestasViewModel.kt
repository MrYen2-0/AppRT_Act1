package com.yuen.encuestasockets.feature.encuestas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.yuen.encuestasockets.feature.encuestas.domain.entities.Encuesta
import com.yuen.encuestasockets.feature.encuestas.domain.entities.OpcionEncuesta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EncuestasUiState(
    val encuestas: List<Encuesta> = emptyList(),
    val encuestaSeleccionada: Encuesta? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class CrearEncuestaUiState(
    val titulo: String = "",
    val opciones: List<String> = listOf("", ""),
    val isCreating: Boolean = false,
    val error: String? = null
)

class EncuestasViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EncuestasUiState())
    val uiState: StateFlow<EncuestasUiState> = _uiState.asStateFlow()

    private val _crearUiState = MutableStateFlow(CrearEncuestaUiState())
    val crearUiState: StateFlow<CrearEncuestaUiState> = _crearUiState.asStateFlow()

    init {
        cargarEncuestas()
    }

    private fun cargarEncuestas() {
        val encuestasMock = listOf(
            Encuesta(
                id = 1,
                titulo = "Encuesta",
                opciones = listOf(
                    OpcionEncuesta(1, "Algo", 0),
                    OpcionEncuesta(2, "o asi", 0),
                    OpcionEncuesta(3, "Hola", 0)
                ),
                creador = "usuario1",
                timestamp = "1:26 p.m."
            ),
            Encuesta(
                id = 2,
                titulo = "Encuesta 2",
                opciones = listOf(
                    OpcionEncuesta(1, "Opción A", 3),
                    OpcionEncuesta(2, "Opción B", 5)
                ),
                creador = "usuario2",
                timestamp = "2:15 p.m."
            )
        )

        _uiState.value = _uiState.value.copy(encuestas = encuestasMock)
    }

    fun cargarEncuesta(encuestaId: Int) {
        val encuesta = _uiState.value.encuestas.find { it.id == encuestaId }
        _uiState.value = _uiState.value.copy(encuestaSeleccionada = encuesta)
    }

    fun votarOpcion(opcionId: Int, username: String) {
        val encuesta = _uiState.value.encuestaSeleccionada ?: return

        val opcionesActualizadas = encuesta.opciones.map { opcion ->
            if (opcion.id == opcionId) {
                if (opcion.votantes.contains(username)) {
                    opcion.copy(
                        votos = opcion.votos - 1,
                        votantes = opcion.votantes - username
                    )
                } else {
                    opcion.copy(
                        votos = opcion.votos + 1,
                        votantes = opcion.votantes + username
                    )
                }
            } else {
                opcion
            }
        }

        val encuestaActualizada = encuesta.copy(opciones = opcionesActualizadas)
        _uiState.value = _uiState.value.copy(encuestaSeleccionada = encuestaActualizada)
    }

    fun onTituloChange(titulo: String) {
        _crearUiState.value = _crearUiState.value.copy(titulo = titulo)
    }

    fun onOpcionChange(index: Int, texto: String) {
        val opcionesActualizadas = _crearUiState.value.opciones.toMutableList()
        opcionesActualizadas[index] = texto
        _crearUiState.value = _crearUiState.value.copy(opciones = opcionesActualizadas)
    }

    fun agregarOpcion() {
        val nuevasOpciones = _crearUiState.value.opciones + ""
        _crearUiState.value = _crearUiState.value.copy(opciones = nuevasOpciones)
    }

    fun eliminarOpcion(index: Int) {
        if (_crearUiState.value.opciones.size > 2) {
            val nuevasOpciones = _crearUiState.value.opciones.toMutableList()
            nuevasOpciones.removeAt(index)
            _crearUiState.value = _crearUiState.value.copy(opciones = nuevasOpciones)
        }
    }

    fun crearEncuesta(username: String, onSuccess: () -> Unit) {
        val titulo = _crearUiState.value.titulo.trim()
        val opciones = _crearUiState.value.opciones.map { it.trim() }

        if (titulo.isBlank()) {
            _crearUiState.value = _crearUiState.value.copy(error = "El título no puede estar vacío")
            return
        }

        if (opciones.any { it.isBlank() }) {
            _crearUiState.value = _crearUiState.value.copy(error = "Todas las opciones deben tener texto")
            return
        }

        _crearUiState.value = _crearUiState.value.copy(isCreating = true, error = null)

        val nuevaEncuesta = Encuesta(
            id = _uiState.value.encuestas.size + 1,
            titulo = titulo,
            opciones = opciones.mapIndexed { index, texto ->
                OpcionEncuesta(index + 1, texto, 0)
            },
            creador = username,
            timestamp = "Ahora"
        )

        val encuestasActualizadas = _uiState.value.encuestas + nuevaEncuesta
        _uiState.value = _uiState.value.copy(encuestas = encuestasActualizadas)

        _crearUiState.value = CrearEncuestaUiState()

        onSuccess()
    }
}