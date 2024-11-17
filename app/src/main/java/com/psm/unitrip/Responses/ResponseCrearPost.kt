package com.psm.unitrip.Responses

import com.psm.unitrip.Models.ChatData

data class ResponseCrearPost(
    val success: Boolean,
    val msg: String,
    val errors: List<String>
)
