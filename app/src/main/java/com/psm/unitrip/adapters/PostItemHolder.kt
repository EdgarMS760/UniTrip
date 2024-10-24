package com.psm.unitrip.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.R
import com.psm.unitrip.classes.ChatItem
import com.psm.unitrip.classes.PostItem
import java.text.SimpleDateFormat
import java.util.Locale

class PostItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.imagePublicacion)
    val usernameView: TextView = view.findViewById(R.id.username)
    val ubicacionView: TextView = view.findViewById(R.id.ubicacion)
    val tituloView: TextView = view.findViewById(R.id.tituloPublicacion)
    val precioView: TextView = view.findViewById(R.id.precioPublicacion)
    val descripcionView: TextView = view.findViewById(R.id.descripcionPublicacion)
    val fechaView: TextView = view.findViewById(R.id.fechaPublicacion)

//    fun render(PostItemModel: PostItem, onClick: (PostItem) -> Unit){
//        nameChat.setText(chatItemModel.name)
//        lastMsg.setText(chatItemModel.lastMsg)
//        val dateFormat = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale("es", "ES"))
//        val formattedDate = dateFormat.format(chatItemModel.timeLastMsg)
//        lastMsgTime.setText(formattedDate)
//
//        itemView.setOnClickListener {
//            onClick(chatItemModel)
//        }
//    }
}