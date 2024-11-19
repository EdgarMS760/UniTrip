package com.psm.unitrip.API

import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.Requests.LogInRequest
import com.psm.unitrip.Requests.RegisterRequest
import com.psm.unitrip.Requests.UpdateRequest
import com.psm.unitrip.Responses.ResponseLogIn
import com.psm.unitrip.Responses.ResponseRegister
import com.psm.unitrip.Responses.ResponseSinglePost
import com.psm.unitrip.Responses.ResponseSync
import com.psm.unitrip.Responses.ResponseSyncUpdated
import com.psm.unitrip.Responses.ResponseUpdate
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface UserService {
    @Headers("Content-Type: application/json")
    @POST("users/logIn")
    fun logIn(
        @Body logInBody: LogInRequest
    ): Call<ResponseLogIn>


    @Headers("Content-Type: application/json")
    @POST("users/register")
    fun register(
        @Body registerBody: RegisterRequest
    ): Call<ResponseRegister>

    @Headers("Content-Type: application/json")
    @POST("users/update")
    fun update(
        @Body updateBody: UpdateRequest
    ): Call<ResponseUpdate>


    @Headers("Content-Type: application/json")
    @POST("users/updatePhoto")
    fun updatePhoto(
        @Body updateBody: UpdateRequest
    ): Call<ResponseUpdate>

    @GET("users/sync/{fechaSync}")
    fun getSync(@Path("fechaSync") fechaSync: String): Call<ResponseSync>

    @GET("users/syncUp/{fechaSync}")
    fun getSyncUp(@Path("fechaSync") fechaSync: String): Call<ResponseSyncUpdated>

}