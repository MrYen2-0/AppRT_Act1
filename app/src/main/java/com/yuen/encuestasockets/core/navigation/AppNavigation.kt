package com.yuen.encuestasockets.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yuen.encuestasockets.core.di.ViewModelFactory
import com.yuen.encuestasockets.feature.auth.presentation.views.LoginScreen
import com.yuen.encuestasockets.feature.auth.presentation.views.RegistroScreen
import com.yuen.encuestasockets.feature.auth.presentation.viewmodel.AuthViewModel
import com.yuen.encuestasockets.feature.encuestas.presentation.views.*
import com.yuen.encuestasockets.feature.encuestas.presentation.viewmodel.EncuestasViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val viewModel: AuthViewModel = viewModel(factory = viewModelFactory)
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { username ->
                    navController.navigate(Screen.EncuestasList.createRoute(username)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate(Screen.Registro.route)
                }
            )
        }

        composable(Screen.Registro.route) {
            val viewModel: AuthViewModel = viewModel(factory = viewModelFactory)
            RegistroScreen(
                viewModel = viewModel,
                onRegistroSuccess = { username ->
                    navController.navigate(Screen.EncuestasList.createRoute(username)) {
                        popUpTo(Screen.Registro.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.EncuestasList.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val viewModel: EncuestasViewModel = viewModel(factory = viewModelFactory)
            EncuestasListScreen(
                username = username,
                viewModel = viewModel,
                onEncuestaClick = { encuestaId ->
                    navController.navigate(Screen.DetalleEncuesta.createRoute(encuestaId))
                },
                onCrearEncuesta = {
                    navController.navigate(Screen.CrearEncuesta.route)
                }
            )
        }

        composable(
            route = Screen.DetalleEncuesta.route,
            arguments = listOf(navArgument("encuestaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val encuestaId = backStackEntry.arguments?.getInt("encuestaId") ?: 0
            val viewModel: EncuestasViewModel = viewModel(factory = viewModelFactory)
            DetalleEncuestaScreen(
                encuestaId = encuestaId,
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.CrearEncuesta.route) {
            val viewModel: EncuestasViewModel = viewModel(factory = viewModelFactory)
            CrearEncuestaScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                },
                onCrear = {
                    navController.popBackStack()
                }
            )
        }
    }
}