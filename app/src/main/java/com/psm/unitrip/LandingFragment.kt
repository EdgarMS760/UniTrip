package com.psm.unitrip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.adapters.PostItemAdapter
import com.psm.unitrip.classes.PostItem

class LandingFragment : Fragment(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_landing, container, false)
        val searchBtn = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtn.setOnClickListener(this)
        val publicaciones = listOf(
            PostItem(R.drawable.background, "@gigakai", "Av. Camino del Valle 2247...", "Voy a Cerralvo", "$500.00", "Salida desde Pedro de Alba...", "27/01/2024 11:30:10 PM"),
            PostItem(R.drawable.background, "@EdgarMs760", "Av. pablo livas 587", "raid a la uni", "$20", "Salida desde el cine pablo livas", "24/10/2024 11:30:10 PM"),
            PostItem(R.drawable.background, "@user1", "Calle Falsa 123...", "Voy a Monterrey", "$300.00", "Salida desde Plaza Central...", "15/02/2024 08:00:00 AM"),
        PostItem(R.drawable.background, "@user2", "Av. Siempre Viva 742...", "Voy a Guadalupe", "$450.00", "Salida desde Estadio...", "20/02/2024 02:30:00 PM"),
        PostItem(R.drawable.background, "@user3", "Calle de la Amargura 666...", "Voy a San Pedro", "$600.00", "Salida desde Centro Comercial...", "05/03/2024 05:45:00 PM"),
        PostItem(R.drawable.background, "@user4", "Boulevard de los Sueños Rotos...", "Voy a Apodaca", "$350.00", "Salida desde Terminal Norte...", "10/03/2024 09:15:00 AM"),
        PostItem(R.drawable.background, "@user5", "Avenida de la Paz 101...", "Voy a Escobedo", "$400.00", "Salida desde Parque Fundidora...", "25/03/2024 07:00:00 PM")

        )

        val recyclerView = root.findViewById<RecyclerView>(R.id.RecyclerPostList)
        recyclerView.layoutManager = LinearLayoutManager(context)
       recyclerView.adapter = PostItemAdapter(publicaciones)
        return root
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.searchBtnDir){
            findNavController().navigate(R.id.action_landingFragment_self)
        }
    }

}