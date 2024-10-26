package com.psm.unitrip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class SignUpFragment : Fragment(), OnClickListener {

    private val PICK_IMAGE_REQUEST = 1
    private var listener: OnFragmentWelcomeActionsListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentWelcomeActionsListener) {
            this.listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val backSignUpBtn = root.findViewById<ImageButton>(R.id.backSignUpBtn)
        backSignUpBtn.setOnClickListener(this)
        val nextSBtn = root.findViewById<Button>(R.id.nextSBtn)
        nextSBtn.setOnClickListener(this)
        val uploadImgBtn = root.findViewById<Button>(R.id.uploadImgBtn)
        uploadImgBtn.setOnClickListener(this)
        val signUpViewS = root.findViewById<TextView>(R.id.signUpViewS)
        signUpViewS.setOnClickListener(this)


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
        when (p0!!.id) {
            R.id.backSignUpBtn -> {
                this.listener?.moveNextPage(1)
            }

            R.id.nextSBtn -> {
                this.listener?.moveNextPage(4)
            }

            R.id.signUpViewS -> {
                this.listener?.moveNextPage(2)
            }

            R.id.uploadImgBtn -> {
                this.abrirGaleria()
            }
        }
    }

}