package com.psm.unitrip.Requests

data class UpdateRequest (
    val idUsuario: Int,
    val email: String,
    val username: String,
    val password: String,
    val phone: String,
    val profilePic: String
)