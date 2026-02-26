package com.yuen.encuestasockets.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioResponse(
    val id: Int,
    val username: String,
    val created_at: String
)

@Serializable
data class RegistroResponse(
    val message: String,
    val usuario: UsuarioResponse
)

@Serializable
data class LoginResponse(
    val message: String,
    val usuario: UsuarioResponse
)

@Serializable
data class ErrorResponse(
    val detail: String
)