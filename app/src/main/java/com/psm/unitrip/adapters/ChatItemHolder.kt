package com.psm.unitrip.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.R
import com.psm.unitrip.classes.ChatItem
import java.text.SimpleDateFormat
import java.util.Locale

class ChatItemHolder(view: View): RecyclerView.ViewHolder(view) {

    val nameChat = view.findViewById<TextView>(R.id.usernameItem)
    val lastMsg = view.findViewById<TextView>(R.id.lastMsgItem)
    val lastMsgTime = view.findViewById<TextView>(R.id.lastMsgTimeItem)

    fun render(chatItemModel: ChatItem,  onClick: (ChatItem) -> Unit){
        nameChat.setText(chatItemModel.name)
        lastMsg.setText(chatItemModel.lastMsg)
        val dateFormat = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale("es", "ES"))
        val formattedDate = dateFormat.format(chatItemModel.timeLastMsg)
        lastMsgTime.setText(formattedDate)

        itemView.setOnClickListener {
            onClick(chatItemModel)
        }
    }
}