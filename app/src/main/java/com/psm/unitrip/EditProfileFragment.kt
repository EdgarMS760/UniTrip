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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Manager.PreferenceManager
import com.psm.unitrip.Manager.UserManager
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.UserApplication.Companion.dbHelper
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class EditProfileFragment : Fragment(), OnClickListener {
    private lateinit var profileImageView: ShapeableImageView
    private lateinit var passwordTxt: EditText
    private lateinit var phoneTxt: EditText
    private lateinit var usernameTxt: EditText
    private var imageChange: Boolean = false

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

        val usuario = SessionManager.getUsuario()

        if(usuario !== null){
            passwordTxt.setText(usuario.password)
            usernameTxt.setText(usuario.username)
            phoneTxt.setText(usuario.telefono)

            val strImage:String =  usuario.profilePic.replace("data:image/*;base64,","")
            val byteArray =  Base64.getDecoder().decode(strImage)

            if(byteArray != null){
                profileImageView.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
            }

        }

        return root
    }


    private fun mostrarAlerta() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Descartar Cambios")
        builder.setMessage("¿Estás seguro de que no quieres guardar los cambios?")


        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
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
                    imageChange = true
                }
            }
        }

    private fun mostrarNoInternet() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Error de Conexion")
        builder.setMessage("No tienes conexion a internet")


        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }


        val dialog = builder.create()
        dialog.show()
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
            mostrarAlerta()
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

            if(NetworkUtils.isNetworkAvailable(requireContext())){
                var isValid = true

                var regexPhone = Regex("^(?:\\+52)? ?\\d{10}\$|^(?:\\+52)? ?\\(?\\d{2,3}\\)? ?\\d{3} ?\\d{4}\$")
                var regexUser = Regex("^[0-9A-Za-zÑñÁáÉéÍíÓóÚú\\s]{2,20}\$")
                val regexPassword = Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[(!”#\$%&\\/=?¡¿:;,._+*{})]).{8,50}\$")



                val password = passwordTxt.text.toString()
                val phone = phoneTxt.text.toString()
                val username = usernameTxt.text.toString()



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
                    val factory = ManagerFactory(RestEngine.getRestEngine())
                    val userManager = factory.createManager(Usuario::class.java)
                    val user = SessionManager.getUsuario()

                    if(imageChange){
                        val imageUri = (profileImageView.drawable as? BitmapDrawable)?.bitmap

                        if(imageUri !== null){
                            val profilePfp = ImageUtilities.getByteArrayFromBitmap(imageUri)
                            val encodedString:String =  Base64.getEncoder().encodeToString(profilePfp)

                            val strEncodeImage:String = "data:image/*;base64," + encodedString


                            (userManager as? UserManager)?.updatePhoto(Usuario(user!!.idUsuario, user.email, password, "", "", username, phone, "", strEncodeImage)) { success ->
                                if(success){
                                    dbHelper.updateUsuario(Usuario(user!!.idUsuario, user.email, password, "", "", username, phone, "", strEncodeImage))
                                    SessionManager.saveSession(requireContext())
                                    val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                        timeZone = TimeZone.getTimeZone("UTC")
                                    }
                                    val currentDate = utcFormat.format(Date())

                                    PreferenceManager.setLastSync(requireContext(), currentDate)
                                    Toast.makeText(this.requireContext(), "Se actualizo con exito", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                                }else{
                                    Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    }else{
                        userManager?.update(Usuario(user!!.idUsuario, user.email, password, "", "", username, phone, "", "")){success ->
                            if(success){
                                dbHelper.updateUsuario(Usuario(user!!.idUsuario, user.email, password, "", "", username, phone, "", ""))
                                SessionManager.saveSession(requireContext())
                                val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                    timeZone = TimeZone.getTimeZone("UTC")
                                }
                                val currentDate = utcFormat.format(Date())

                                PreferenceManager.setLastSync(requireContext(), currentDate)
                                Toast.makeText(this.requireContext(), "Se actualizo con exito", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                            }else{
                                Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }else{
                    Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
                }
            }else{
                mostrarNoInternet()
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