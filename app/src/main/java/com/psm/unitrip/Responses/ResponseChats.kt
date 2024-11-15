package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Mensaje

data class ResponseChats(
    val success: Boolean,
    val chats: List<Chat>,
    val msg: String,
    val errors: List<String>
)
