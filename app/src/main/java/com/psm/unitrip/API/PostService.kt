package com.psm.unitrip.API

import com.psm.unitrip.Requests.CreatePostRequest
import com.psm.unitrip.Responses.ResponseCrearPost
import com.psm.unitrip.Responses.ResponsePosts
import com.psm.unitrip.Responses.ResponseSinglePost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @Headers("Content-Type: application/json")
    @POST("posts/postear")
    fun crearPost(
        @Body crearPostBody: CreatePostRequest
    ): Call<ResponseCrearPost>


    @GET("posts/getMisPosts/{idUsuario}")
    fun getPostsUsuario(@Path("idUsuario") idUsuario: Int): Call<ResponsePosts>

    @GET("posts/getPosts/{idUsuario}")
    fun obtenerPosts(@Path("idUsuario") idUsuario: Int): Call<ResponsePosts>

    @GET("posts/getPost/{idPost}")
    fun getPost(@Path("idPost") idPost: Int): Call<ResponseSinglePost>

}