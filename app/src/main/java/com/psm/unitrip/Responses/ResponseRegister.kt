package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Usuario

data class ResponseRegister (
    val success: Boolean,
    val data: Usuario,
    val msg: String,
    val errors: List<String>
)