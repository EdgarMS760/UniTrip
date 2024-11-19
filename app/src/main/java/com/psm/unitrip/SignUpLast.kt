package com.psm.unitrip

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.UserApplication.Companion.dbHelper
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.classes.RegistroViewModel
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import java.io.ByteArrayOutputStream
import java.util.Base64

class SignUpLast : Fragment(), OnClickListener {
    private var listener: OnFragmentWelcomeActionsListener? = null
    private lateinit var usernameTxt: EditText
    private lateinit var phoneTxt: EditText
    private lateinit var direccionTxt: EditText
    val registroViewModel: RegistroViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnFragmentWelcomeActionsListener){
            this.listener =  context
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
        val root = inflater.inflate(R.layout.fragment_sign_up_last, container, false)
        val backLastBtn =  root.findViewById<ImageButton>(R.id.backLastBtn)
        backLastBtn.setOnClickListener(this)
        val registerBtn =  root.findViewById<Button>(R.id.registerBtn)
        registerBtn.setOnClickListener(this)

        usernameTxt = root.findViewById<EditText>(R.id.usernameTxtSU)
        phoneTxt = root.findViewById<EditText>(R.id.phoneTxt)
        direccionTxt = root.findViewById<EditText>(R.id.addressTxt)

        return root
    }

    private fun mostrarAlerta() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Error de Registro")
        builder.setMessage("Ya te encuentras registrado")


        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }


        val dialog = builder.create()
        dialog.show()
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


    fun getByteArrayFromBitmap(bitmap: Bitmap?):ByteArray{
        val stream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG,80, stream)
        return stream.toByteArray()
    }


    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.backLastBtn->{
                this.listener?.moveNextPage(4)
            }
            R.id.registerBtn->{

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

                    val username = usernameTxt.text.toString()
                    val phone = phoneTxt.text.toString()
                    val address = direccionTxt.text.toString()

                    if(!regexUser.matches(username)){
                        isValid = false
                        usernameTxt.setBackgroundResource(R.drawable.input_sytle_error)
                    }else{
                        usernameTxt.setBackgroundResource(R.drawable.input_style)
                    }

                    if(phone.isNotEmpty()){
                        if(!regexPhone.matches(phone)){
                            isValid = false
                            phoneTxt.setBackgroundResource(R.drawable.input_sytle_error)
                        }else{
                            phoneTxt.setBackgroundResource(R.drawable.input_style)
                        }
                    }else{
                        phoneTxt.setBackgroundResource(R.drawable.input_style)
                    }


                    if(address.isNotEmpty()){
                        if(address.length > 150){
                            isValid = false
                            direccionTxt.setBackgroundResource(R.drawable.input_sytle_error)
                        }else{
                            direccionTxt.setBackgroundResource(R.drawable.input_style)
                        }
                    }else{
                        direccionTxt.setBackgroundResource(R.drawable.input_style)
                    }

                    if(isValid){
                        val factory = ManagerFactory(RestEngine.getRestEngine())
                        val userManager = factory.createManager(Usuario::class.java)

                        val profilePfp = this.getByteArrayFromBitmap(registroViewModel.photoUri)
                        val encodedString:String =  Base64.getEncoder().encodeToString(profilePfp)

                        val strEncodeImage:String = "data:image/*;base64," + encodedString

                        if(userManager !== null){
                            userManager.add(Usuario(0, registroViewModel.email.toString(), registroViewModel.password.toString(), registroViewModel.nombre.toString(), registroViewModel.apellido.toString(), username, phone, address, strEncodeImage)){success ->
                                if(success){
                                    Toast.makeText(requireContext(),"Se registro correctamente", Toast.LENGTH_LONG).show()
                                    SessionManager.saveSession(requireContext())
                                    dbHelper.insertUsuario(Usuario(0, registroViewModel.email.toString(), registroViewModel.password.toString(), registroViewModel.nombre.toString(), registroViewModel.apellido.toString(), username, phone, address, strEncodeImage))
                                    val intent =  Intent(requireContext(), MainActivity::class.java)
                                    startActivity(intent)
                                }else{
                                    mostrarAlerta()
                                }
                            }

                        }



                    }else{
                        Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    this.mostrarNoInternet()
                }



            }
        }
    }


}