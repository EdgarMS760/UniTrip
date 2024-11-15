package com.psm.unitrip.Manager

import android.widget.Toast
import com.psm.unitrip.API.UserService
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.Requests.RegisterRequest
import com.psm.unitrip.Requests.UpdateRequest
import com.psm.unitrip.Responses.ResponseLogIn
import com.psm.unitrip.Responses.ResponseRegister
import com.psm.unitrip.Responses.ResponseUpdate
import com.psm.unitrip.classes.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserManager(private val api: UserService): Manager<Usuario> {
    override fun add(item: Usuario, callback: (Boolean)-> Unit) {
        try {
            val response: Call<ResponseRegister> = api.register(RegisterRequest(item.email, item.password, item.profilePic, item.nombre, item.apellido, item.username, item.direccion, item.telefono))

            response.enqueue(object: Callback<ResponseRegister> {
                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                    callback(false)
                }

                override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                SessionManager.register(body.data)
                                callback(true)
                            }else{
                                callback(false)
                            }
                        }else{
                            callback(false)
                        }
                    }else{
                        callback(false)
                    }
                }
            })

        } catch (e: Exception) {
            println("Error añadiendo post: ${e.message}")
        }
    }

    override fun update(item: Usuario, callback: (Boolean)-> Unit) {
        try {
            val response: Call<ResponseUpdate> = api.update(UpdateRequest(item.idUsuario, item.email, item.username, item.password, item.telefono, ""))

            response.enqueue(object: Callback<ResponseUpdate> {
                override fun onFailure(call: Call<ResponseUpdate>, t: Throwable) {
                    callback(false)
                }

                override fun onResponse(call: Call<ResponseUpdate>, response: Response<ResponseUpdate>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                SessionManager.update(body.data)
                                callback(true)
                            }else{
                                callback(false)
                            }
                        }else{
                            callback(false)
                        }
                    }else{
                        callback(false)
                    }
                }
            })

        } catch (e: Exception) {
            println("Error añadiendo post: ${e.message}")
        }
    }

    public fun updatePhoto(item: Usuario, callback: (Boolean)-> Unit) {
        try {
            val response: Call<ResponseUpdate> = api.updatePhoto(UpdateRequest(item.idUsuario, item.email, item.username, item.password, item.telefono, item.profilePic))

            response.enqueue(object: Callback<ResponseUpdate> {
                override fun onFailure(call: Call<ResponseUpdate>, t: Throwable) {
                    callback(false)
                }

                override fun onResponse(call: Call<ResponseUpdate>, response: Response<ResponseUpdate>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                SessionManager.update(body.data)
                                callback(true)
                            }else{
                                callback(false)
                            }
                        }else{
                            callback(false)
                        }
                    }else{
                        callback(false)
                    }
                }
            })


        } catch (e: Exception) {
            println("Error añadiendo post: ${e.message}")
        }
    }


    override fun getAll(id: Int, callback: (List<Usuario>?) -> Unit) {
        TODO("Not yet implemented")
    }



}