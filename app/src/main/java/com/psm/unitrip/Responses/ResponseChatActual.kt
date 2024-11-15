package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.ChatData
import com.psm.unitrip.Models.Mensaje

data class ResponseChatActual(
    val success: Boolean,
    val data: ChatData,
    val msg: String,
    val errors: List<String>
)
