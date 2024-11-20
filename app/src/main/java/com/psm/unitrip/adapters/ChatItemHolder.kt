package com.psm.unitrip.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.R
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.classes.ChatItem
import com.psm.unitrip.classes.SessionManager
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Locale

class ChatItemHolder(view: View): RecyclerView.ViewHolder(view) {

    val nameChat = view.findViewById<TextView>(R.id.usernameItem)
    val pfpChat = view.findViewById<ImageView>(R.id.userChatImg)
    val lastMsg = view.findViewById<TextView>(R.id.lastMsgItem)
    val lastMsgTime = view.findViewById<TextView>(R.id.lastMsgTimeItem)


    fun render(chatItemModel: Chat,  onClick: (Chat) -> Unit){

        if(SessionManager.getUsuario()?.idUsuario != chatItemModel.idEmisor){
            nameChat.setText(chatItemModel.nombreEmisor)
            val strImage:String =  chatItemModel.fotoEmisor.replace("data:image/*;base64,","")
            val byteArray =  Base64.getDecoder().decode(strImage)

            if(byteArray != null){
                pfpChat.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
            }

        }else{
            nameChat.setText(chatItemModel.nombreReceptor)

            val strImage:String =  chatItemModel.fotoReceptor.replace("data:image/*;base64,","")
            val byteArray =  Base64.getDecoder().decode(strImage)

            if(byteArray != null){
                pfpChat.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
            }
        }

        itemView.setOnClickListener {
            itemView.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    itemView.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            onClick(chatItemModel)
        }
    }
}