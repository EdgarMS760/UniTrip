package com.psm.unitrip.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.R
import com.psm.unitrip.classes.ChatItem

class ChatItemAdapter(private val ChatItemList: List<ChatItem>, private val onClick: (ChatItem) -> Unit): RecyclerView.Adapter<ChatItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return ChatItemHolder(layoutinflater.inflate(R.layout.chatitem, parent, false))
    }

    override fun getItemCount(): Int {
        return ChatItemList.size
    }

    override fun onBindViewHolder(holder: ChatItemHolder, position: Int) {
        val item = ChatItemList[position]
        holder.render(item, onClick)
    }

}