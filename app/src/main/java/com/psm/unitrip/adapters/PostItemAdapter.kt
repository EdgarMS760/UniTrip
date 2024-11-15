package com.psm.unitrip.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.Models.Post
import com.psm.unitrip.R
import com.psm.unitrip.classes.ChatItem
import com.psm.unitrip.classes.PostItem

class PostItemAdapter(private var postList: List<Post>, private val onClick: (Post) -> Unit): RecyclerView.Adapter<PostItemHolder>() {

    fun updatePosts(newPosts: List<Post>) {
        postList = newPosts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return PostItemHolder(view)
    }

    override fun onBindViewHolder(holder: PostItemHolder, position: Int) {
        val item = postList[position]
        holder.render(item, onClick)

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}