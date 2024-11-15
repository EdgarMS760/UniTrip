package com.psm.unitrip.Manager

import com.psm.unitrip.API.ChatService
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.Requests.CreateChatRequest
import com.psm.unitrip.Requests.SendMsgRequest
import com.psm.unitrip.Responses.ResponseChatActual
import com.psm.unitrip.Responses.ResponseChats
import com.psm.unitrip.Responses.ResponseCreateChat
import com.psm.unitrip.Responses.ResponseSendMsg
import com.psm.unitrip.Responses.ResponseUpdate
import com.psm.unitrip.classes.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatManager(private val api: ChatService): Manager<Chat> {
    override fun add(item: Chat, callback: (Boolean)-> Unit) {
        try {
            val response: Call<ResponseCreateChat> = api.createChat(CreateChatRequest(item.idEmisor, item.idReceptor))

            response.enqueue(object: Callback<ResponseCreateChat> {
                override fun onFailure(call: Call<ResponseCreateChat>, t: Throwable) {
                    callback(false)
                }

                override fun onResponse(call: Call<ResponseCreateChat>, response: Response<ResponseCreateChat>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                callback(true)
                            }else{
                                callback(false)
                            }
                        }
                    }else{
                        callback(false)
                    }
                }
            })
        }catch (e: Exception) {
            println("Error añadiendo post: ${e.message}")
        }
    }


     fun createChatPost(item: Chat, callback: (Chat?)-> Unit) {
        try {
            val response: Call<ResponseCreateChat> = api.createChat(CreateChatRequest(item.idEmisor, item.idReceptor))

            response.enqueue(object: Callback<ResponseCreateChat> {
                override fun onFailure(call: Call<ResponseCreateChat>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<ResponseCreateChat>, response: Response<ResponseCreateChat>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                callback(body.data.chatInfo)
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
            println("Error añadiendo post: ${e.message}")
        }
    }


    override fun getAll(id: Int, callback: (List<Chat>?) -> Unit) {
        try {
            val response: Call<ResponseChats> = api.obtenerChats(id)

            response.enqueue(object: Callback<ResponseChats> {
                override fun onFailure(call: Call<ResponseChats>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<ResponseChats>, response: Response<ResponseChats>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                callback(body.chats)
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
            println("Error añadiendo post: ${e.message}")
        }
    }



    fun getMessages(id: Int, callback: (List<Mensaje>?) -> Unit) {
        try {
            val response: Call<ResponseChatActual> = api.obtenerChatActual(id)

            response.enqueue(object: Callback<ResponseChatActual> {
                override fun onFailure(call: Call<ResponseChatActual>, t: Throwable) {
                    callback(null)
                }

                override fun onResponse(call: Call<ResponseChatActual>, response: Response<ResponseChatActual>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            println("Aqui")
                            if(body.success){
                                callback(body.data.mensajes)
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
            println("Error añadiendo post: ${e.message}")
        }
    }


    fun sendMsg(idUsuario: Int, idChat: Int, messageSend: String, callback: (Boolean) -> Unit) {
        try {
            val response: Call<ResponseSendMsg> = api.sendMsg(SendMsgRequest(idUsuario, idChat, messageSend))

            response.enqueue(object: Callback<ResponseSendMsg> {
                override fun onFailure(call: Call<ResponseSendMsg>, t: Throwable) {
                    callback(false)
                }

                override fun onResponse(call: Call<ResponseSendMsg>, response: Response<ResponseSendMsg>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                callback(true)
                            }else{
                                callback(false)
                            }
                        }
                    }else{
                        callback(false)
                    }
                }
            })
        }catch (e: Exception) {
            println("Error añadiendo post: ${e.message}")
        }
    }

}