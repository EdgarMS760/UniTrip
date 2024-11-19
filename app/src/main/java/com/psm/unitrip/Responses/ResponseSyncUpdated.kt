package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario

data class ResponseSyncUpdated(
    val success: Boolean,
    val msg: String,
    val errors: List<String>,
    val posts: List<Post>,
    val usuarios: List<Usuario>
)
