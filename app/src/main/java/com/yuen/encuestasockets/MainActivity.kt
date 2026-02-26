package com.yuen.encuestasockets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yuen.encuestasockets.core.di.ViewModelFactory
import com.yuen.encuestasockets.core.navigation.AppNavigation
import com.yuen.encuestasockets.core.ui.theme.EncuestaSocketsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EncuestaSocketsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EncuestaSocketsApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EncuestaSocketsApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModelFactory = ViewModelFactory()

    AppNavigation(
        navController = navController,
        viewModelFactory = viewModelFactory
    )
}