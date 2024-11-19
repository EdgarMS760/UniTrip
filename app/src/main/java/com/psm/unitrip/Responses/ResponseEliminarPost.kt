package com.psm.unitrip.Responses

data class ResponseEliminarPost(
    val success: Boolean,
    val msg: String,
    val errors: List<String>
)
