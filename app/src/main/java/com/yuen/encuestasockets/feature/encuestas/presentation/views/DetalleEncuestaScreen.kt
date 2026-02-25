package com.yuen.encuestasockets.feature.encuestas.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuen.encuestasockets.feature.encuestas.presentation.viewmodel.EncuestasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEncuestaScreen(
    encuestaId: Int,
    viewModel: EncuestasViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(encuestaId) {
        viewModel.cargarEncuesta(encuestaId)
    }

    val encuesta = uiState.encuestaSeleccionada

    Scaffold(
        containerColor = Color(0xFF1A1A1A),
        topBar = {
            TopAppBar(
                title = { Text("Encuesta", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", color = Color.White, fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2A2A2A)
                )
            )
        }
    ) { padding ->
        if (encuesta != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    text = encuesta.titulo,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Selecciona una opción o más.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val totalVotos = encuesta.opciones.sumOf { it.votos }

                encuesta.opciones.forEach { opcion ->
                    val porcentaje = if (totalVotos > 0) opcion.votos.toFloat() / totalVotos else 0f
                    OpcionEncuesta(
                        texto = opcion.texto,
                        votos = opcion.votos,
                        porcentaje = porcentaje,
                        isSelected = opcion.votantes.contains("usuario1"),
                        onClick = {
                            viewModel.votarOpcion(opcion.id, "usuario1")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = encuesta.timestamp,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Ver votos",
                        color = Color(0xFF00C853),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OpcionEncuesta(
    texto: String,
    votos: Int,
    porcentaje: Float,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(porcentaje)
                .fillMaxHeight()
                .background(
                    color = if (isSelected) Color(0xFF00C853).copy(alpha = 0.3f) else Color.Transparent,
                    shape = RoundedCornerShape(28.dp)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = if (isSelected) Color(0xFF00C853) else Color.Transparent,
                            shape = CircleShape
                        )
                        .then(
                            if (!isSelected) Modifier.background(
                                Color.Gray.copy(alpha = 0.3f),
                                CircleShape
                            ) else Modifier
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = texto,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Text(
                text = votos.toString(),
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}