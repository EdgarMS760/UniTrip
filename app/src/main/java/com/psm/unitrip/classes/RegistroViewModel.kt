package com.psm.unitrip.classes

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel

class RegistroViewModel: ViewModel() {
    var email: String? = null
    var photoUri: Bitmap? = null
    var nombre: String? = null
    var apellido: String? = null
    var password: String? = null
    var celular: String? = null
    var direccion: String? = null
    var username: String? = null

    
    fun reset(){
        email = null
        photoUri = null
        nombre = null
        apellido = null
        password = null
        celular = null
        direccion = null
        username = null
    }
}