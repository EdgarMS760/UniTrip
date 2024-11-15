package com.psm.unitrip.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.R
import com.psm.unitrip.classes.MessageItem

class MessageItemAdapter(private var MessageItemList: MutableList<Mensaje>): RecyclerView.Adapter<MessageItemHolder>() {
    fun updateMensajes(newMsg: List<Mensaje>) {
        MessageItemList = newMsg.toMutableList()
        notifyDataSetChanged()
    }

    fun addMsg(newMsg: Mensaje) {
        val position = MessageItemList.size
        MessageItemList.add(newMsg)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return MessageItemHolder(layoutinflater.inflate(R.layout.message_item, parent, false))
    }

    override fun getItemCount(): Int {
        return MessageItemList.size
    }

    override fun onBindViewHolder(holder: MessageItemHolder, position: Int) {
        val item = MessageItemList[position]
        holder.render(item)
    }
}