package com.psm.unitrip.API

import com.psm.unitrip.Requests.CreateChatRequest
import com.psm.unitrip.Requests.SendMsgRequest
import com.psm.unitrip.Responses.ResponseChatActual
import com.psm.unitrip.Responses.ResponseChats
import com.psm.unitrip.Responses.ResponseCreateChat
import com.psm.unitrip.Responses.ResponseSendMsg
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {
    @GET("chats/getChats/{idUsuario}")
    fun obtenerChats(@Path("idUsuario") idUsuario: Int): Call<ResponseChats>

    @GET("chats/getChatActual/{idChat}")
    fun obtenerChatActual(@Path("idChat") idChat: Int): Call<ResponseChatActual>

    @Headers("Content-Type: application/json")
    @POST("chats/enviar")
    fun sendMsg(
        @Body messageBody: SendMsgRequest
    ): Call<ResponseSendMsg>


    @Headers("Content-Type: application/json")
    @POST("chats/crear")
    fun createChat(
        @Body chatBody: CreateChatRequest
    ): Call<ResponseCreateChat>


}