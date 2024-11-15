package com.psm.unitrip.Models

import android.graphics.Bitmap
import java.time.LocalDateTime

data class Post(
    val idPost: Int,
    val title: String,
    val description: String,
    val precio: String,
    val status: String,
    val location: String,
    val idUsuario: Int,
    val username: String,
    val profilePic: String,
    val arrayImagenes: List<String>,
    val fechaCreacion: String
)
