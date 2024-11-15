package com.psm.unitrip

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.google.android.material.imageview.ShapeableImageView
import com.psm.unitrip.classes.RegistroViewModel

class SignUpFragment : Fragment(), OnClickListener {
    private lateinit var profileImageView: ShapeableImageView
    private lateinit var emailTxt: EditText
    val registroViewModel: RegistroViewModel by activityViewModels()

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

        emailTxt = root.findViewById<EditText>(R.id.emailSTxt)
        profileImageView = root.findViewById(R.id.userPfpImg)

        if(registroViewModel.email != null){
            emailTxt.setText(registroViewModel.email)
        }


        if(registroViewModel.photoUri != null){
            profileImageView.setImageBitmap(registroViewModel.photoUri)
        }

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
                if (uriImagenSeleccionada != null) {
                    profileImageView.setImageURI(uriImagenSeleccionada)
                }
            }
        }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.backSignUpBtn -> {
                this.listener?.moveNextPage(1)
            }

            R.id.nextSBtn -> {
                p0.animate()
                    .alpha(0.5f)
                    .setDuration(300)
                    .withEndAction {
                        p0.animate()
                            .alpha(1f)
                            .setDuration(300)
                    }
                var isValid = true
                val email = emailTxt.text.toString()
                val regex = Regex("^([a-z]+)([a-z0-9\\.]*)@([a-z0-9]+)((\\.[a-z]{2,3})+)\$")
                val imageUri = (profileImageView.drawable as? BitmapDrawable)?.bitmap

                if(imageUri == null){
                    isValid = false
                }

                if(!regex.matches(email)){
                    isValid = false
                    emailTxt.setBackgroundResource(R.drawable.input_sytle_error)
                }else{
                    emailTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(isValid){
                    registroViewModel.email = email
                    registroViewModel.photoUri = imageUri
                    this.listener?.moveNextPage(4)
                }else{
                    Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.uploadImgBtn -> {
                p0.animate()
                    .alpha(0.5f)
                    .setDuration(300)
                    .withEndAction {
                        p0.animate()
                            .alpha(1f)
                            .setDuration(300)
                    }
                this.abrirGaleria()
            }
        }
    }

}