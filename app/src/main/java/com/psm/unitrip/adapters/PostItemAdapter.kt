package com.psm.unitrip.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.R
import com.psm.unitrip.classes.PostItem

class PostItemAdapter(private val posts: List<PostItem>) : RecyclerView.Adapter<PostItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imagePublicacion)
        val usernameView: TextView = view.findViewById(R.id.username)
        val ubicacionView: TextView = view.findViewById(R.id.ubicacion)
        val tituloView: TextView = view.findViewById(R.id.tituloPublicacion)
        val precioView: TextView = view.findViewById(R.id.precioPublicacion)
        val descripcionView: TextView = view.findViewById(R.id.descripcionPublicacion)
        val fechaView: TextView = view.findViewById(R.id.fechaPublicacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val publicacion = posts[position]
        holder.imageView.setImageResource(publicacion.imagen)
        holder.usernameView.text = publicacion.username
        holder.ubicacionView.text = publicacion.ubicacion
        holder.tituloView.text = publicacion.titulo
        holder.precioView.text = publicacion.precio
        holder.descripcionView.text = publicacion.descripcion
        holder.fechaView.text = publicacion.fecha
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}