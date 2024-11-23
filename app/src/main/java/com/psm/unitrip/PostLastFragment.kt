package com.psm.unitrip

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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Manager.PreferenceManager
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.UserApplication.Companion.dbHelper
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.classes.CreatePostViewModel
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class PostLastFragment : Fragment(), OnClickListener {
    private lateinit var locationTxt: EditText
    private lateinit var priceTxt: EditText
    private lateinit var btnPostC: Button
    val createPostViewModel: CreatePostViewModel by activityViewModels()
    private var lastClickTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_post_last, container, false)
        val searchBtnDir = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtnDir.setOnClickListener(this)
        val btnCancelC = root.findViewById<Button>(R.id.btnCancelC)
        btnCancelC.setOnClickListener(this)
        btnCancelC.setBackgroundResource(R.drawable.button_cancel)

        val btnSaveC = root.findViewById<Button>(R.id.btnSaveC)
        btnSaveC.setOnClickListener(this)
        btnPostC = root.findViewById<Button>(R.id.btnPostC)
        btnPostC.setOnClickListener(this)
        val backCreateBtn = root.findViewById<ImageButton>(R.id.backCreateBtn)
        backCreateBtn.setOnClickListener(this)


        priceTxt = root.findViewById<EditText>(R.id.ETPriceCreatePost)
        locationTxt = root.findViewById<EditText>(R.id.ETLocationCreate)

        return root
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
        if(p0!!.id == R.id.searchBtnDir){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime
                findNavController().navigate(R.id.action_postLastFragment_to_searchFragment)
            }


        }

        if(p0!!.id == R.id.btnCancelC){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime
                findNavController().navigate(R.id.action_postLastFragment_to_profileFragment)
            }


        }

        if(p0!!.id == R.id.btnSaveC){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime


                var isValid = true
                val regexPrice = Regex("^[0-9]+([.,][0-9]{1,2})?\$")
                val regexDomicilio = Regex("^([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*(\\d+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+)\$")

                val locationStr = locationTxt.text.toString()
                val price = priceTxt.text.toString()

                if(!regexPrice.matches(price)){
                    isValid = false
                    priceTxt.setBackgroundResource(R.drawable.input_sytle_error)
                    priceTxt.error = "Precio con caracteres invalidos"
                }else{
                    priceTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(!regexDomicilio.matches(locationStr)){
                    isValid = false
                    locationTxt.setBackgroundResource(R.drawable.input_sytle_error)
                    locationTxt.error = "Direccion con formato Invalido"
                }else{
                    locationTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(isValid){
                    val listImg64: MutableList<String> = mutableListOf()
                    createPostViewModel.images?.forEach { item ->
                        val img = ImageUtilities.getByteArrayFromBitmap(item)
                        val encodedString:String =  Base64.getEncoder().encodeToString(img)

                        val strEncodeImage:String = "data:image/*;base64," + encodedString
                        listImg64.add(strEncodeImage)
                    }
                    if(SessionManager.getIsLoggedIn()){
                        dbHelper.insertDraft(Post(0, createPostViewModel.title.toString(), createPostViewModel.descripcion.toString(), price, "A", locationStr, SessionManager.getUsuario()!!.idUsuario, "", "", listImg64, ""))
                        Toast.makeText(this.requireContext(), "Se guardo el draft con exito", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_postLastFragment_to_profileFragment)
                    }

                }
            }
        }

        if(p0!!.id == R.id.btnPostC){
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
                val regexPrice = Regex("^[0-9]+([.,][0-9]{1,2})?\$")
                val regexDomicilio = Regex("^([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*(\\d+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+)\$")

                val locationStr = locationTxt.text.toString()
                val price = priceTxt.text.toString()

                if(!regexPrice.matches(price)){
                    isValid = false
                    priceTxt.setBackgroundResource(R.drawable.input_sytle_error)
                    priceTxt.error = "Precio con caracteres invalidos"
                }else{
                    priceTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(!regexDomicilio.matches(locationStr)){
                    isValid = false
                    locationTxt.setBackgroundResource(R.drawable.input_sytle_error)
                    locationTxt.error = "Direccion con formato Invalido, Ej: Camino, 2247, Sierra, China, Jalisco"
                }else{
                    locationTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(isValid){
                    val listImg64: MutableList<String> = mutableListOf()
                    createPostViewModel.images?.forEach { item ->
                        val img = ImageUtilities.getByteArrayFromBitmap(item)
                        val encodedString:String =  Base64.getEncoder().encodeToString(img)

                        val strEncodeImage:String = "data:image/*;base64," + encodedString
                        listImg64.add(strEncodeImage)
                    }
                    if(SessionManager.getIsLoggedIn()){
                        val factory = ManagerFactory(RestEngine.getRestEngine())
                        val postManager = factory.createManager(Post::class.java)
                        btnPostC.isEnabled = false
                        postManager!!.add(Post(0, createPostViewModel.title.toString(), createPostViewModel.descripcion.toString(), price, "A", locationStr, SessionManager.getUsuario()!!.idUsuario, "", "", listImg64, "")){success->
                            btnPostC.isEnabled = true
                            if(success){
                                val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                    timeZone = TimeZone.getTimeZone("UTC")
                                }
                                val currentDate = utcFormat.format(Date())

                                PreferenceManager.setLastSync(requireContext(), currentDate)
                                dbHelper.insertPost(Post(0, createPostViewModel.title.toString(), createPostViewModel.descripcion.toString(), price, "A", locationStr, SessionManager.getUsuario()!!.idUsuario, "", "", listImg64, ""))
                                Toast.makeText(this.requireContext(), "Se añadio el post con exito", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_postLastFragment_to_profileFragment)
                            }
                        }

                    }

                }
            }else{
                mostrarNoInternet()
            }

        }

        if(p0!!.id == R.id.backCreateBtn){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500){
                lastClickTime = currentTime
                findNavController().navigate(R.id.action_postLastFragment_to_postFragment)
            }

        }

    }

}