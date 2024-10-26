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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

class PostFragment : Fragment(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_post, container, false)
        val searchBtnDir = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtnDir.setOnClickListener(this)
        val btnPostSig = root.findViewById<Button>(R.id.btnPostSig)
        btnPostSig.setOnClickListener(this)
        val btnSubirFoto = root.findViewById<Button>(R.id.btnSubirFoto)
        btnSubirFoto.setOnClickListener(this)

        return root
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        seleccionarImagen.launch(intent)
    }

    private val seleccionarImagen =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val uriImagenSeleccionada = result.data?.data
                println("Imagen seleccionada: $uriImagenSeleccionada")
            }
        }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.btnPostSig){
            findNavController().navigate(R.id.action_postFragment_to_postLastFragment)
        }
        if(p0!!.id == R.id.searchBtnDir){
            findNavController().navigate(R.id.action_postFragment_to_searchFragment)
        }
        if(p0!!.id == R.id.btnSubirFoto){
            this.abrirGaleria()
        }
    }

}