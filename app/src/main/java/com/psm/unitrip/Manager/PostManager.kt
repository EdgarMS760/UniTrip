package com.psm.unitrip.Manager

import com.psm.unitrip.API.PostService
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Responses.ResponseChatActual
import com.psm.unitrip.Responses.ResponsePosts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostManager(private val api: PostService): Manager<Post> {
    override fun add(item: Post, callback: (Boolean)-> Unit) {
        callback(true)
    }

    override fun delete(id: Int) {

    }

    override fun getAll(id: Int, callback: (List<Post>?) -> Unit){
        try {
            val response: Call<ResponsePosts> = api.obtenerPosts(id)

            response.enqueue(object: Callback<ResponsePosts> {
                override fun onFailure(call: Call<ResponsePosts>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<ResponsePosts>, response: Response<ResponsePosts>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                callback(body.posts)
                            }else{
                                callback(null)
                            }
                        }
                    }else{
                        callback(null)
                    }
                }
            })
        }catch (e: Exception) {
            println("Error obteniendo posts: ${e.message}")
        }
    }

    fun getMyPosts(id: Int, callback: (List<Post>?) -> Unit){
        try {
            val response: Call<ResponsePosts> = api.getPostsUsuario(id)

            response.enqueue(object: Callback<ResponsePosts> {
                override fun onFailure(call: Call<ResponsePosts>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<ResponsePosts>, response: Response<ResponsePosts>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                callback(body.posts)
                            }else{
                                callback(null)
                            }
                        }
                    }else{
                        callback(null)
                    }
                }
            })
        }catch (e: Exception) {
            println("Error obteniendo posts: ${e.message}")
        }
    }

}