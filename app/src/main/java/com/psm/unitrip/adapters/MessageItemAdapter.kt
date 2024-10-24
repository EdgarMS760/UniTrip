package com.psm.unitrip.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.R
import com.psm.unitrip.classes.MessageItem

class MessageItemAdapter(private val MessageItemList: List<MessageItem>): RecyclerView.Adapter<MessageItemHolder>() {
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