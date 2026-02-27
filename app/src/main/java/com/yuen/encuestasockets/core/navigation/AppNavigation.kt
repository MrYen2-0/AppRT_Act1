package com.yuen.encuestasockets.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yuen.encuestasockets.feature.auth.presentation.views.LoginScreen
import com.yuen.encuestasockets.feature.auth.presentation.views.RegistroScreen
import com.yuen.encuestasockets.feature.auth.presentation.viewmodel.AuthViewModel
import com.yuen.encuestasockets.feature.encuestas.presentation.views.*
import com.yuen.encuestasockets.feature.encuestas.presentation.viewmodel.EncuestasViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { username, userId ->
                    navController.navigate(Screen.EncuestasList.createRoute(username, userId)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate(Screen.Registro.route)
                }
            )
        }

        composable(Screen.Registro.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            RegistroScreen(
                viewModel = viewModel,
                onRegistroSuccess = { username, userId ->
                    navController.navigate(Screen.EncuestasList.createRoute(username, userId)) {
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
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val viewModel: EncuestasViewModel = hiltViewModel()
            EncuestasListScreen(
                username = username,
                viewModel = viewModel,
                onEncuestaClick = { encuestaId ->
                    navController.navigate(Screen.DetalleEncuesta.createRoute(encuestaId, userId))
                },
                onCrearEncuesta = {
                    navController.navigate(Screen.CrearEncuesta.route)
                }
            )
        }

        composable(
            route = Screen.DetalleEncuesta.route,
            arguments = listOf(
                navArgument("encuestaId") { type = NavType.IntType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val encuestaId = backStackEntry.arguments?.getInt("encuestaId") ?: 0
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val viewModel: EncuestasViewModel = hiltViewModel()
            DetalleEncuestaScreen(
                encuestaId = encuestaId,
                usuarioId = userId,
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.CrearEncuesta.route) {
            val viewModel: EncuestasViewModel = hiltViewModel()
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
