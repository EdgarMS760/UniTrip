package com.psm.unitrip.Responses

import com.psm.unitrip.Models.Post


data class ResponsePosts(
    val success: Boolean,
    val posts: List<Post>,
    val msg: String,
    val errors: List<String>
)
