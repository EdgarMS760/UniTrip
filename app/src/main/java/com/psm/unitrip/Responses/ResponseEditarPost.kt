package com.psm.unitrip.Responses

data class ResponseEditarPost(
    val success: Boolean,
    val msg: String,
    val errors: List<String>
)
