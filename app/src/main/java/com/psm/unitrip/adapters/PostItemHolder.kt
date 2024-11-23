package com.psm.unitrip.adapters

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.psm.unitrip.Models.Post
import com.psm.unitrip.R
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.classes.ChatItem
import com.psm.unitrip.classes.PostItem
import com.psm.unitrip.classes.SessionManager
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Locale

class PostItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ViewPager2 = view.findViewById(R.id.imagePublicacion)
    val usernameView: TextView = view.findViewById(R.id.username)
    val ubicacionView: TextView = view.findViewById(R.id.ubicacion)
    val tituloView: TextView = view.findViewById(R.id.tituloPublicacion)
    val precioView: TextView = view.findViewById(R.id.precioPublicacion)
    val descripcionView: TextView = view.findViewById(R.id.descripcionPublicacion)
    val fechaView: TextView = view.findViewById(R.id.fechaPublicacion)
    val statusView: TextView = view.findViewById(R.id.status)
    val iconoEdit: ImageView = view.findViewById(R.id.iconoEdit)
    val userPostImg: ImageView = view.findViewById(R.id.userPostImg)
    val tabIndicator: TabLayout = view.findViewById(R.id.tabIndicator)

    fun render(PostItemModel: Post, onClick: (Post) -> Unit){
        var listImages: MutableList<Bitmap> = mutableListOf()

        PostItemModel.arrayImagenes.forEach { image ->
            val strImage:String =  image.replace("data:image/*;base64,","")
            val byteArray =  Base64.getDecoder().decode(strImage)

            if(byteArray != null){
                listImages.add(ImageUtilities.getBitMapFromByteArray(byteArray))
            }
        }


        if(PostItemModel.status == "A"){
            statusView.setText("Activo")
        }else{
            statusView.setText("Borrador")
        }

        if(listImages.isNotEmpty()){
            imageView.adapter = ImageAdapter(listImages.toList());
            TabLayoutMediator(tabIndicator, imageView) { _, _ -> }.attach()

        }
        usernameView.text = PostItemModel.username
        ubicacionView.text = PostItemModel.location
        tituloView.text = PostItemModel.title
        precioView.text = PostItemModel.precio
        descripcionView.text = PostItemModel.description
        fechaView.text = PostItemModel.fechaCreacion
        if(PostItemModel.idUsuario != SessionManager.getUsuario()?.idUsuario){
            iconoEdit.setBackgroundResource(R.drawable.baseline_forum_24)
        }else{
            iconoEdit.setBackgroundResource(R.drawable.baseline_edit_square_24)
        }

        val strImage:String =  PostItemModel.profilePic.replace("data:image/*;base64,","")
        val byteArray =  Base64.getDecoder().decode(strImage)

        if(byteArray != null){
            userPostImg.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
        }


        iconoEdit.setOnClickListener {
            iconoEdit.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    iconoEdit.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            onClick(PostItemModel)
        }
    }
}