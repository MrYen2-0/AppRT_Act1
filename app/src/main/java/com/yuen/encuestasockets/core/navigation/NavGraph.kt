package com.yuen.encuestasockets.core.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registro : Screen("registro")
    object EncuestasList : Screen("encuestas_list/{username}") {
        fun createRoute(username: String) = "encuestas_list/$username"
    }
    object DetalleEncuesta : Screen("detalle_encuesta/{encuestaId}") {
        fun createRoute(encuestaId: Int) = "detalle_encuesta/$encuestaId"
    }
    object CrearEncuesta : Screen("crear_encuesta")
}