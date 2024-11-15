package com.psm.unitrip.Requests

data class RegisterRequest(
    val email: String,
    val password: String,
    val profilePic: String,
    val nombre: String,
    val apellido: String,
    val username: String,
    val direccion: String,
    val telefono: String
)
