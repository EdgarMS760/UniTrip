package com.psm.unitrip.Manager

import com.psm.unitrip.API.ChatService
import com.psm.unitrip.API.PostService
import com.psm.unitrip.API.UserService
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario
import retrofit2.Retrofit

class ManagerFactory(private val retrofit: Retrofit) {
    fun <T> createManager(type: Class<T>): Manager<T>? {
        return when (type) {
            Post::class.java -> PostManager(retrofit.create(PostService::class.java)) as Manager<T>
            Chat::class.java -> ChatManager(retrofit.create(ChatService::class.java)) as Manager<T>
            Usuario::class.java -> UserManager(retrofit.create(UserService::class.java)) as Manager<T>
            else -> null
        }
    }
}