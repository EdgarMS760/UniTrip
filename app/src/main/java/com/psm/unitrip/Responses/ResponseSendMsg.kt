package com.psm.unitrip.Responses

data class ResponseSendMsg(
    val success: Boolean,
    val msg: String,
    val errors: List<String>
)
