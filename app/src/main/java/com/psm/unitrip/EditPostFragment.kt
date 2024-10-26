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


class EditPostFragment : Fragment(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_edit_post, container, false)
        val searchBtnDir = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtnDir.setOnClickListener(this)
        val btnPostSig = root.findViewById<Button>(R.id.btnPostEPSig)
        btnPostSig.setOnClickListener(this)
        val btnSubirFotoEdit = root.findViewById<Button>(R.id.btnSubirFotoEdit)
        btnSubirFotoEdit.setOnClickListener(this)

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
        if(p0!!.id == R.id.btnPostEPSig){
            findNavController().navigate(R.id.action_editPostFragment_to_editPostLastFragment)
        }
        if(p0!!.id == R.id.searchBtnDir){
            findNavController().navigate(R.id.action_editPostFragment_to_searchFragment)
        }
        if(p0!!.id == R.id.btnSubirFotoEdit){
            this.abrirGaleria()
        }
    }

}