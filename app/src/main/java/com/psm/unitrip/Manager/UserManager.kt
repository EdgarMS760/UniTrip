package com.psm.unitrip.Manager

import android.widget.Toast
import com.psm.unitrip.API.UserService
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.Requests.RegisterRequest
import com.psm.unitrip.Requests.UpdateRequest
import com.psm.unitrip.Responses.ResponseLogIn
import com.psm.unitrip.Responses.ResponseRegister
import com.psm.unitrip.Responses.ResponseSync
import com.psm.unitrip.Responses.ResponseSyncUpdated
import com.psm.unitrip.Responses.ResponseUpdate
import com.psm.unitrip.UserApplication
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

    public fun sync(dateSync: String, callback: (Boolean)-> Unit) {
        try {
            val response: Call<ResponseSync> = api.getSync(dateSync)

            response.enqueue(object: Callback<ResponseSync> {
                override fun onFailure(call: Call<ResponseSync>, t: Throwable) {
                    callback(false)
                }

                override fun onResponse(call: Call<ResponseSync>, response: Response<ResponseSync>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){
                                body.usuarios.forEach { usuario ->
                                    UserApplication.dbHelper.insertUsuario(Usuario(0, usuario.email, usuario.password, usuario.nombre, usuario.apellido, usuario.username, usuario.telefono, usuario.direccion, usuario.profilePic))
                                }

                                body.posts.forEach { post ->
                                    UserApplication.dbHelper.insertPost(Post(0, post.title, post.description, post.precio, post.status, post.location, post.idUsuario, "", "", post.arrayImagenes, ""))
                                }

                                body.chats.forEach { chat ->
                                    UserApplication.dbHelper.insertChats(Chat(0, chat.idEmisor, chat.idReceptor, "", "", "", "", "", ""))
                                }

                                body.mensajes.forEach { mensaje ->
                                    UserApplication.dbHelper.insertMensaje(Mensaje(mensaje.idEmisor, mensaje.mensaje, mensaje.idChatPerteneciente, mensaje.fechaCreacion))
                                }
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
            println("Error sync: ${e.message}")
        }
    }

    public fun syncUpdated(dateSync: String, callback: (Boolean)-> Unit) {
        try {
            val response: Call<ResponseSyncUpdated> = api.getSyncUp(dateSync)

            response.enqueue(object: Callback<ResponseSyncUpdated> {
                override fun onFailure(call: Call<ResponseSyncUpdated>, t: Throwable) {
                    callback(false)
                }

                override fun onResponse(call: Call<ResponseSyncUpdated>, response: Response<ResponseSyncUpdated>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body !== null){
                            if(body.success){

                                body.usuarios.forEach { usuario ->
                                    UserApplication.dbHelper.updateUsuario(Usuario(usuario.idUsuario, usuario.email, usuario.password, "", "", usuario.username, usuario.telefono, "", usuario.profilePic))
                                }

                                body.posts.forEach { post ->
                                    UserApplication.dbHelper.updatePost(Post(post.idPost, post.title, post.description, post.precio, "A", post.location, post.idUsuario, "", "", post.arrayImagenes, ""))
                                }
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
            println("Error sync: ${e.message}")
        }
    }


}