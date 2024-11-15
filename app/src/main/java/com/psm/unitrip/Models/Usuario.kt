package com.psm.unitrip.Models

import android.graphics.Bitmap

data class Usuario(
    val idUsuario: Int,
    val email: String,
    var password: String,
    val nombre: String,
    val apellido: String,
    var username: String,
    var telefono: String,
    val direccion: String,
    var profilePic: String,
)
