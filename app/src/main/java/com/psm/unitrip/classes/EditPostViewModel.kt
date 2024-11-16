package com.psm.unitrip.classes

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel

class EditPostViewModel: ViewModel() {
    var title: String? = null
    var descripcion: String? = null
    var location: String? = null
    var price: String? = null
    var images: MutableList<Uri>? = null
    var imagesAnt: List<Bitmap>? = null
    var idPost: Int? = null

    fun reset(){
        title = null
        descripcion = null
        location = null
        price = null
        images = null
        idPost = null
        imagesAnt = null
    }
}