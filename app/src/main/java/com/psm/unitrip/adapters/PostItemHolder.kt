package com.psm.unitrip.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.psm.unitrip.R
import com.psm.unitrip.classes.ChatItem
import com.psm.unitrip.classes.PostItem
import java.text.SimpleDateFormat
import java.util.Locale

class PostItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ViewPager2 = view.findViewById(R.id.imagePublicacion)
    val usernameView: TextView = view.findViewById(R.id.username)
    val ubicacionView: TextView = view.findViewById(R.id.ubicacion)
    val tituloView: TextView = view.findViewById(R.id.tituloPublicacion)
    val precioView: TextView = view.findViewById(R.id.precioPublicacion)
    val descripcionView: TextView = view.findViewById(R.id.descripcionPublicacion)
    val fechaView: TextView = view.findViewById(R.id.fechaPublicacion)
    val iconoEdit: ImageView = view.findViewById(R.id.iconoEdit)

    fun render(PostItemModel: PostItem, onClick: (PostItem) -> Unit){
        imageView.adapter = ImageAdapter(PostItemModel.imagenes);
        usernameView.text = PostItemModel.username
        ubicacionView.text = PostItemModel.ubicacion
        tituloView.text = PostItemModel.titulo
        precioView.text = PostItemModel.precio
        descripcionView.text = PostItemModel.descripcion
        fechaView.text = PostItemModel.fecha

        iconoEdit.setOnClickListener {

            onClick(PostItemModel)
        }
    }
}