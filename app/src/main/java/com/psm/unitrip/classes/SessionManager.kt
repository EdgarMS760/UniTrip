package com.psm.unitrip.classes

import android.content.Context
import android.widget.Toast
import com.psm.unitrip.API.UserService
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.Requests.LogInRequest
import com.psm.unitrip.Responses.ResponseLogIn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SessionManager {
    private var UsuarioActual: Usuario? = null
    private var isLoggedIn = false

    fun getUsuario(): Usuario? {
        return UsuarioActual;
    }

    fun getIsLoggedIn(): Boolean {
        return isLoggedIn;
    }

    fun logIn(email: String, password: String, context: Context, callback: (Boolean) -> Unit){
        val requestB = LogInRequest(email, password)

        val service: UserService = RestEngine.getRestEngine().create(UserService::class.java)
        val response: Call<ResponseLogIn> = service.logIn(requestB)

        response.enqueue(object: Callback<ResponseLogIn> {
            override fun onFailure(call: Call<ResponseLogIn>, t: Throwable) {
                Toast.makeText(context,"Error", Toast.LENGTH_LONG).show()
                callback(false)
            }

            override fun onResponse(call: Call<ResponseLogIn>, response: Response<ResponseLogIn>) {
                if(response.isSuccessful){
                    val body = response.body()
                    UsuarioActual = body!!.data;
                    isLoggedIn = true
                    callback(true)
                }else{
                    callback(false)
                }
            }
        })

    }

    fun register(userRegistrado: Usuario){
        UsuarioActual = userRegistrado
        isLoggedIn = true
    }

    fun update(userActualizado: Usuario){
        UsuarioActual = userActualizado
    }

    fun saveSession(){

    }

    fun loadSession(){

    }

    fun logOut(){
        this.UsuarioActual = null
        this.isLoggedIn = false
    }

}