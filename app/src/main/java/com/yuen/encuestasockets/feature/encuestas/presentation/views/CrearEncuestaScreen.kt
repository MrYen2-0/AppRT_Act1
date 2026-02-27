package com.yuen.encuestasockets.feature.encuestas.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
fun CrearEncuestaScreen(
    viewModel: EncuestasViewModel,
    onBack: () -> Unit,
    onCrear: () -> Unit
) {
    val uiState by viewModel.crearUiState.collectAsState()

    Scaffold(
        containerColor = Color(0xFF1A1A1A),
        topBar = {
            TopAppBar(
                title = { Text("Crear Encuesta", color = Color.White) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.titulo,
                onValueChange = { viewModel.onTituloChange(it) },
                label = { Text("Título de la encuesta", color = Color.Gray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Opciones",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            uiState.opciones.forEachIndexed { index, opcion ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = opcion,
                        onValueChange = { viewModel.onOpcionChange(index, it) },
                        label = { Text("Opción ${index + 1}", color = Color.Gray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF00C853),
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    if (uiState.opciones.size > 2) {
                        IconButton(
                            onClick = { viewModel.eliminarOpcion(index) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar opción",
                                tint = Color.Red
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            TextButton(
                onClick = { viewModel.agregarOpcion() }
            ) {
                Text(
                    text = "+ Agregar opción",
                    color = Color(0xFF00C853),
                    fontSize = 14.sp
                )
            }

            if (uiState.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.error ?: "",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.crearEncuesta(
                        onSuccess = onCrear
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00C853)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                enabled = !uiState.isCreating
            ) {
                if (uiState.isCreating) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Crear Encuesta",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}