package com.psm.unitrip.Responses

import com.psm.unitrip.Models.ChatData

data class ResponseCreateChat(
    val success: Boolean,
    val data: ChatData,
    val msg: String,
    val errors: List<String>
)
