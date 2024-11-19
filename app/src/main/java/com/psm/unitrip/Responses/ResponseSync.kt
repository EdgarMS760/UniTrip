package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.ChatSync
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario

data class ResponseSync(
    val success: Boolean,
    val msg: String,
    val errors: List<String>,
    val posts: List<Post>,
    val usuarios: List<Usuario>,
    val mensajes: List<Mensaje>,
    val chats: List<ChatSync>
)
