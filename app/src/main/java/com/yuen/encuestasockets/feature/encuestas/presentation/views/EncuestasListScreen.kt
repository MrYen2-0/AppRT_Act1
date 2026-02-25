package com.yuen.encuestasockets.feature.encuestas.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuen.encuestasockets.feature.encuestas.presentation.viewmodel.EncuestasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncuestasListScreen(
    username: String,
    viewModel: EncuestasViewModel,
    onEncuestaClick: (Int) -> Unit,
    onCrearEncuesta: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Color(0xFF1A1A1A),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hola, $username",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2A2A2A)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCrearEncuesta,
                containerColor = Color(0xFF00C853)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear encuesta",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(uiState.encuestas) { encuesta ->
                EncuestaItem(
                    titulo = encuesta.titulo,
                    totalVotos = encuesta.opciones.sumOf { it.votos },
                    onClick = { onEncuestaClick(encuesta.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun EncuestaItem(
    titulo: String,
    totalVotos: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$totalVotos votos",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}