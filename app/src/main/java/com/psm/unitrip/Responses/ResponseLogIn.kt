package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Usuario

data class ResponseLogIn(
    val success: Boolean,
    val data: Usuario,
    val msg: String
)
