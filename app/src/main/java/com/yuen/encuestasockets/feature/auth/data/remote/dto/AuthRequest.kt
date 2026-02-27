package com.yuen.encuestasockets.feature.auth.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)