package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Post

data class ResponseSinglePost (
    val success: Boolean,
    val post: Post,
    val msg: String,
    val errors: List<String>
)