package com.psm.unitrip.Requests

data class CreatePostRequest(
    val titulo: String,
    val descripcion: String,
    val precio: String,
    val status:String,
    val location:String,
    val idUsuario:Int,
    val imagenes: List<String>
)
