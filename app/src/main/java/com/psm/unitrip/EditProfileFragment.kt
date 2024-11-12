package com.psm.unitrip

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView

class EditProfileFragment : Fragment(), OnClickListener {
    private lateinit var profileImageView: ShapeableImageView
    private lateinit var passwordTxt: EditText
    private lateinit var phoneTxt: EditText
    private lateinit var usernameTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val btnCancel = root.findViewById<Button>(R.id.btnCancelEdit)
        val btnSave = root.findViewById<Button>(R.id.btnSaveEdit)
        val btnPhoto = root.findViewById<Button>(R.id.uploadImgBtnE)
        btnCancel.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnPhoto.setOnClickListener(this)

        profileImageView = root.findViewById<ShapeableImageView>(R.id.userPfpEdit)
        passwordTxt = root.findViewById<EditText>(R.id.passEditTxt)
        usernameTxt = root.findViewById<EditText>(R.id.userEditTxt)
        phoneTxt = root.findViewById<EditText>(R.id.phoneEditTxt)

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
        if (p0!!.id == R.id.btnCancelEdit) {
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
        if (p0!!.id == R.id.btnSaveEdit) {
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }

            var isValid = true

            var regexPhone = Regex("^(?:\\+52)? ?\\d{10}\$|^(?:\\+52)? ?\\(?\\d{2,3}\\)? ?\\d{3} ?\\d{4}\$")
            var regexUser = Regex("^[0-9A-Za-zÑñÁáÉéÍíÓóÚú\\s]{2,20}\$")
            val regexPassword = Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[(!”#\$%&\\/=?¡¿:;,._+*{})]).{8,50}\$")

            val imageUri = (profileImageView.drawable as? BitmapDrawable)?.bitmap

            val password = passwordTxt.text.toString()
            val phone = phoneTxt.text.toString()
            val username = usernameTxt.text.toString()

            if(imageUri == null){
                isValid = false
            }

            if(!regexPassword.matches(password)){
                isValid = false
                passwordTxt.setBackgroundResource(R.drawable.input_sytle_error)
            }else{
                passwordTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(!regexPhone.matches(phone)){
                isValid = false
                phoneTxt.setBackgroundResource(R.drawable.input_sytle_error)
            }else{
                phoneTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(!regexUser.matches(username)){
                isValid = false
                usernameTxt.setBackgroundResource(R.drawable.input_sytle_error)
            }else{
                usernameTxt.setBackgroundResource(R.drawable.input_style)
            }


            if(isValid){
                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
            }else{
                Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
            }

        }

        if (p0!!.id == R.id.uploadImgBtnE) {
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            abrirGaleria()
        }
    }

}