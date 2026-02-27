package com.yuen.encuestasockets.feature.encuestas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuen.encuestasockets.feature.encuestas.domain.entities.Encuesta
import com.yuen.encuestasockets.feature.encuestas.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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

@HiltViewModel
class EncuestasViewModel @Inject constructor(
    private val getEncuestasUseCase: GetEncuestasUseCase,
    private val getEncuestaUseCase: GetEncuestaUseCase,
    private val crearEncuestaUseCase: CrearEncuestaUseCase,
    private val votarUseCase: VotarUseCase,
    private val observarVotosUseCase: ObservarVotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EncuestasUiState())
    val uiState: StateFlow<EncuestasUiState> = _uiState.asStateFlow()

    private val _crearUiState = MutableStateFlow(CrearEncuestaUiState())
    val crearUiState: StateFlow<CrearEncuestaUiState> = _crearUiState.asStateFlow()

    init {
        cargarEncuestas()
        observarVotosWebSocket()
    }

    fun cargarEncuestas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            getEncuestasUseCase().fold(
                onSuccess = { encuestas ->
                    _uiState.value = _uiState.value.copy(
                        encuestas = encuestas,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error al cargar encuestas"
                    )
                }
            )
        }
    }

    fun cargarEncuesta(encuestaId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getEncuestaUseCase(encuestaId).fold(
                onSuccess = { encuesta ->
                    _uiState.value = _uiState.value.copy(
                        encuestaSeleccionada = encuesta,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error al cargar encuesta"
                    )
                }
            )
        }
    }

    fun votarOpcion(opcionId: Int, usuarioId: Int) {
        viewModelScope.launch {
            votarUseCase(opcionId, usuarioId).fold(
                onSuccess = {
                    val encuesta = _uiState.value.encuestaSeleccionada
                    if (encuesta != null) {
                        cargarEncuesta(encuesta.id)
                    }
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        error = exception.message ?: "Error al votar"
                    )
                }
            )
        }
    }

    private fun observarVotosWebSocket() {
        viewModelScope.launch {
            try {
                observarVotosUseCase().collect { (opcionId, _) ->
                    val encuestaActual = _uiState.value.encuestaSeleccionada
                    if (encuestaActual != null) {
                        val opcionesActualizadas = encuestaActual.opciones.map { opcion ->
                            if (opcion.id == opcionId) {
                                opcion.copy(votos = opcion.votos + 1)
                            } else {
                                opcion
                            }
                        }
                        _uiState.value = _uiState.value.copy(
                            encuestaSeleccionada = encuestaActual.copy(opciones = opcionesActualizadas)
                        )
                    }
                    val encuestasActualizadas = _uiState.value.encuestas.map { encuesta ->
                        val opcionesActualizadas = encuesta.opciones.map { opcion ->
                            if (opcion.id == opcionId) {
                                opcion.copy(votos = opcion.votos + 1)
                            } else {
                                opcion
                            }
                        }
                        encuesta.copy(opciones = opcionesActualizadas)
                    }
                    _uiState.value = _uiState.value.copy(encuestas = encuestasActualizadas)
                }
            } catch (_: Exception) {
                // WebSocket desconectado
            }
        }
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

    fun crearEncuesta(onSuccess: () -> Unit) {
        val titulo = _crearUiState.value.titulo.trim()
        val opciones = _crearUiState.value.opciones.map { it.trim() }

        _crearUiState.value = _crearUiState.value.copy(isCreating = true, error = null)

        viewModelScope.launch {
            crearEncuestaUseCase(titulo, opciones).fold(
                onSuccess = {
                    _crearUiState.value = CrearEncuestaUiState()
                    cargarEncuestas()
                    onSuccess()
                },
                onFailure = { exception ->
                    _crearUiState.value = _crearUiState.value.copy(
                        isCreating = false,
                        error = exception.message ?: "Error al crear encuesta"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
