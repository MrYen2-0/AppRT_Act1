package com.yuen.encuestasockets.core.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registro : Screen("registro")
    object EncuestasList : Screen("encuestas_list/{username}/{userId}") {
        fun createRoute(username: String, userId: Int) = "encuestas_list/$username/$userId"
    }
    object DetalleEncuesta : Screen("detalle_encuesta/{encuestaId}/{userId}") {
        fun createRoute(encuestaId: Int, userId: Int) = "detalle_encuesta/$encuestaId/$userId"
    }
    object CrearEncuesta : Screen("crear_encuesta")
}
