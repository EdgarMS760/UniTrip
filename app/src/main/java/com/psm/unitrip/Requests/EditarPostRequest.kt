package com.psm.unitrip.Requests

data class EditarPostRequest(
    val titulo: String,
    val descripcion: String,
    val precio: String,
    val status:String,
    val location:String,
    val idPost:Int,
    val imagenes: List<String>
)
