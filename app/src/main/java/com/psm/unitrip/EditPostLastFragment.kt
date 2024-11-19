package com.psm.unitrip

import android.content.Intent
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
import com.psm.unitrip.UserApplication.Companion.dbHelper
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.classes.EditPostViewModel
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class EditPostLastFragment : Fragment(), OnClickListener {
    val editPostViewModel: EditPostViewModel by activityViewModels()
    private lateinit var locationTxt: EditText
    private lateinit var priceTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_edit_post_last, container, false)
        val searchBtnDir = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtnDir.setOnClickListener(this)
        val btnCancelC = root.findViewById<Button>(R.id.btnCancelEPL)
        btnCancelC.setOnClickListener(this)
        val btnSaveC = root.findViewById<Button>(R.id.btnSaveEPL)
        btnSaveC.setOnClickListener(this)
        val backCreateBtn = root.findViewById<ImageButton>(R.id.backEditBtn)
        backCreateBtn.setOnClickListener(this)
        val btnPostC = root.findViewById<Button>(R.id.btnPostC)
        btnPostC.setOnClickListener(this)



        priceTxt = root.findViewById<EditText>(R.id.ETPriceEditPost)
        locationTxt = root.findViewById<EditText>(R.id.ETLocationEdit)


        if(editPostViewModel.location !== null){
            locationTxt.setText(editPostViewModel.location)
        }

        if(editPostViewModel.price !== null){
            priceTxt.setText(editPostViewModel.price)
        }

        return root
    }

    private fun mostrarAlerta() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Eliminar Post")
        builder.setMessage("¿Estás seguro de que quieres eliminar el post?")


        builder.setPositiveButton("Aceptar") { dialog, _ ->

            if(editPostViewModel.status == "A"){

                if(NetworkUtils.isNetworkAvailable(requireContext())){
                    val factory = ManagerFactory(RestEngine.getRestEngine())
                    val postManager = factory.createManager(Post::class.java)

                    postManager!!.delete(editPostViewModel.idPost!!.toInt()){ success->
                        if(success){
                            dbHelper.deletePost(editPostViewModel.idPost!!.toInt())
                            val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                            val currentDate = utcFormat.format(Date())

                            PreferenceManager.setLastSync(requireContext(), currentDate)
                            dialog.dismiss()
                            Toast.makeText(this.requireContext(), "Se elimino con exito", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
                        }
                    }
                }else{
                    dialog.dismiss()
                    mostrarNoInternet()
                }

            }else{
                dbHelper.deleteDraft(editPostViewModel.idPost!!.toInt())
                dialog.dismiss()
                Toast.makeText(this.requireContext(), "Se elimino con exito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
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

            findNavController().navigate(R.id.action_editPostLastFragment_to_searchFragment)
        }

        if(p0!!.id == R.id.btnCancelEPL){
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

        if(p0!!.id == R.id.btnSaveEPL){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }

            if(editPostViewModel.status == "A"){
                Toast.makeText(this.requireContext(), "Tu post ya ha sido posteado", Toast.LENGTH_SHORT).show()
            }else{
                var isValid = true
                val regexPrice = Regex("^[0-9]+([.,][0-9]{1,2})?\$")
                val regexDomicilio = Regex("^([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*(\\d+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+),\\s*([A-Za-zÁáÉéÍíÓóÚúÑñ\\s]+)\$")

                val locationStr = locationTxt.text.toString()
                val price = priceTxt.text.toString()

                if(!regexPrice.matches(price)){
                    isValid = false
                    priceTxt.setBackgroundResource(R.drawable.input_sytle_error)
                }else{
                    priceTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(!regexDomicilio.matches(locationStr)){
                    isValid = false
                    locationTxt.setBackgroundResource(R.drawable.input_sytle_error)
                }else{
                    locationTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(isValid){
                    val listImg64: MutableList<String> = mutableListOf()

                    if(editPostViewModel.images != null){
                        editPostViewModel.images?.forEach { item ->
                            val img = ImageUtilities.getByteArrayFromBitmap(item)
                            val encodedString:String =  Base64.getEncoder().encodeToString(img)

                            val strEncodeImage:String = "data:image/*;base64," + encodedString
                            listImg64.add(strEncodeImage)
                        }
                    }

                    dbHelper.updateDraft(Post(editPostViewModel.idPost!!.toInt(), editPostViewModel.title.toString(), editPostViewModel.descripcion.toString(), price, "D", locationStr, 0, "", "", listImg64, ""))
                    findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
                }else{
                    Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
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
                }else{
                    priceTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(!regexDomicilio.matches(locationStr)){
                    isValid = false
                    locationTxt.setBackgroundResource(R.drawable.input_sytle_error)
                }else{
                    locationTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(isValid){

                    if(editPostViewModel.status == "A"){
                        val listImg64: MutableList<String> = mutableListOf()

                        if(editPostViewModel.images != null){
                            editPostViewModel.images?.forEach { item ->
                                val img = ImageUtilities.getByteArrayFromBitmap(item)
                                val encodedString:String =  Base64.getEncoder().encodeToString(img)

                                val strEncodeImage:String = "data:image/*;base64," + encodedString
                                listImg64.add(strEncodeImage)
                            }
                        }

                        if(SessionManager.getIsLoggedIn()){
                            val factory = ManagerFactory(RestEngine.getRestEngine())
                            val postManager = factory.createManager(Post::class.java)

                            postManager!!.update(Post(editPostViewModel.idPost!!.toInt(), editPostViewModel.title.toString(), editPostViewModel.descripcion.toString(), price, "A", locationStr, SessionManager.getUsuario()!!.idUsuario, "", "", listImg64, "")){ success->
                                if(success){
                                    dbHelper.updatePost(Post(editPostViewModel.idPost!!.toInt(), editPostViewModel.title.toString(), editPostViewModel.descripcion.toString(), price, "A", locationStr, SessionManager.getUsuario()!!.idUsuario, "", "", listImg64, ""))
                                    val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                        timeZone = TimeZone.getTimeZone("UTC")
                                    }
                                    val currentDate = utcFormat.format(Date())

                                    PreferenceManager.setLastSync(requireContext(), currentDate)
                                    Toast.makeText(this.requireContext(), "Se actualizo el post con exito", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
                                }
                            }

                        }


                    }else{
                        val listImg64: MutableList<String> = mutableListOf()

                        if(editPostViewModel.images != null){
                            editPostViewModel.images?.forEach { item ->
                                val img = ImageUtilities.getByteArrayFromBitmap(item)
                                val encodedString:String =  Base64.getEncoder().encodeToString(img)

                                val strEncodeImage:String = "data:image/*;base64," + encodedString
                                listImg64.add(strEncodeImage)
                            }
                        }else{
                            editPostViewModel.imagesAnt?.forEach { item ->
                                listImg64.add(item)
                            }
                        }

                        if(SessionManager.getIsLoggedIn()){
                            val factory = ManagerFactory(RestEngine.getRestEngine())
                            val postManager = factory.createManager(Post::class.java)

                            postManager!!.add(Post(0, editPostViewModel.title.toString(), editPostViewModel.descripcion.toString(), price, "A", locationStr, SessionManager.getUsuario()!!.idUsuario, "", "", listImg64, "")){ success->
                                if(success){
                                    dbHelper.deleteDraft(editPostViewModel.idPost!!.toInt())
                                    dbHelper.insertPost(Post(0, editPostViewModel.title.toString(), editPostViewModel.descripcion.toString(), price, "A", locationStr, SessionManager.getUsuario()!!.idUsuario, "", "", listImg64, ""))
                                    val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                        timeZone = TimeZone.getTimeZone("UTC")
                                    }
                                    val currentDate = utcFormat.format(Date())

                                    PreferenceManager.setLastSync(requireContext(), currentDate)
                                    Toast.makeText(this.requireContext(), "Se actualizo el post con exito", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
                                }
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

        if(p0!!.id == R.id.backEditBtn){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            findNavController().navigate(R.id.action_editPostLastFragment_to_editPostFragment)

        }



    }

}