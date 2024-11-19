package com.psm.unitrip.Models

data class Chat(
    val idChat: Int,
    val idEmisor: Int,
    val idReceptor: Int,
    val nombreReceptor: String,
    val nombreEmisor: String,
    val fotoReceptor: String,
    val fotoEmisor: String,
    val emailEmisor: String,
    val emailReceptor: String
)
