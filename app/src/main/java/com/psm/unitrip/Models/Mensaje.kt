package com.psm.unitrip.Models

import java.time.LocalDateTime

data class Mensaje(
    val idEmisor: Int,
    val mensaje: String,
    val idChatPerteneciente: Int,
    val fechaCreacion: String
)
