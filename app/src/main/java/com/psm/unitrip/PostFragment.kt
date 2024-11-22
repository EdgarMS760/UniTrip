package com.psm.unitrip

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.psm.unitrip.adapters.ImageAdapter
import com.psm.unitrip.adapters.ImageUriAdapter
import com.psm.unitrip.classes.CreatePostViewModel

class PostFragment : Fragment(), OnClickListener {
    private lateinit var imageView: ViewPager2
    private lateinit var titleTxt: EditText
    private lateinit var tabIndicator: TabLayout
    private lateinit var descriptionTxt: EditText
    private var selectedImages = mutableListOf<Bitmap>()
    val createPostViewModel: CreatePostViewModel by activityViewModels()

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

        tabIndicator = root.findViewById<TabLayout>(R.id.tabIndicator)

        val btnPostSig = root.findViewById<Button>(R.id.btnPostSig)
        btnPostSig.setOnClickListener(this)
        val btnSubirFoto = root.findViewById<Button>(R.id.btnSubirFoto)
        btnSubirFoto.setOnClickListener(this)

        titleTxt = root.findViewById<EditText>(R.id.usernameTxtSU)
        descriptionTxt = root.findViewById<EditText>(R.id.DescriptionCreate)
        imageView = root.findViewById<ViewPager2>(R.id.imgIcono)




        if(createPostViewModel.title !== null){
            titleTxt.setText(createPostViewModel.title)
        }

        if(createPostViewModel.descripcion !== null){
            descriptionTxt.setText(createPostViewModel.descripcion)
        }

        if(createPostViewModel.images !== null){
            selectedImages = (createPostViewModel.images as MutableList<Bitmap>)
            imageView.adapter = ImageAdapter(createPostViewModel.images as MutableList<Bitmap>)
            TabLayoutMediator(tabIndicator, imageView) { _, _ -> }.attach()
        }

        return root
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        seleccionarImagen.launch(intent)
    }

    private val seleccionarImagen =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                selectedImages.clear()
                try{
                    result.data?.let { data ->
                        val clipData = data.clipData
                        if (clipData != null) {
                            for (i in 0 until clipData.itemCount) {
                                val uri = clipData.getItemAt(i).uri
                                val bitmap = uriToBitmap(uri)
                                if (bitmap != null) {
                                    selectedImages.add(bitmap)
                                }
                            }
                        } else {
                            data.data?.let { uri ->
                                val bitmap = uriToBitmap(uri)
                                if (bitmap != null) {
                                    selectedImages.add(bitmap)
                                }
                            }
                        }

                        val adapter = ImageAdapter(selectedImages)
                        imageView.adapter = adapter
                        TabLayoutMediator(tabIndicator, imageView) { _, _ -> }.attach()
                    }
                }catch (e: Exception){
                    Toast.makeText(requireContext(), "Ocurrió un error al seleccionar imágenes", Toast.LENGTH_SHORT).show()
                }


            }
        }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.btnPostSig){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            var isValid = true

            val title = titleTxt.text.toString()
            val descripcion = descriptionTxt.text.toString()

            if(selectedImages.isEmpty()){
                isValid = false
            }

            if(title.isEmpty()){
                titleTxt.setBackgroundResource(R.drawable.input_sytle_error)
                isValid = false
            }else{
                titleTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(descripcion.isEmpty()){
                descriptionTxt.setBackgroundResource(R.drawable.input_sytle_error)
                isValid = false
            }else{
                descriptionTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(isValid){
                createPostViewModel.title = title
                createPostViewModel.descripcion = descripcion
                createPostViewModel.images = selectedImages

                findNavController().navigate(R.id.action_postFragment_to_postLastFragment)
            }else{
                Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
            }

        }
        if(p0!!.id == R.id.searchBtnDir){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            findNavController().navigate(R.id.action_postFragment_to_searchFragment)
        }
        if(p0!!.id == R.id.btnSubirFoto){
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