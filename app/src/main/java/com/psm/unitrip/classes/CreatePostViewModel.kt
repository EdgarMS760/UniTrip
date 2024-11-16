package com.psm.unitrip.classes

import android.net.Uri
import androidx.lifecycle.ViewModel

class CreatePostViewModel: ViewModel() {
    var title: String? = null
    var descripcion: String? = null
    var location: String? = null
    var price: String? = null
    var images: MutableList<Uri>? = null

    fun reset(){
        title = null
        descripcion = null
        location = null
        price = null
        images = null
    }
}