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
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.adapters.ImageAdapter
import com.psm.unitrip.adapters.ImageUriAdapter
import com.psm.unitrip.classes.EditPostViewModel
import java.util.Base64


class EditPostFragment : Fragment(), OnClickListener {
    private lateinit var imageView: ViewPager2
    private lateinit var titleTxt: EditText
    private lateinit var tabIndicator: TabLayout
    private lateinit var descriptionTxt: EditText
    private var selectedImages = mutableListOf<Bitmap>()
    val editPostViewModel: EditPostViewModel by activityViewModels()

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

        imageView = root.findViewById<ViewPager2>(R.id.imgIconoEdit)
        titleTxt = root.findViewById<EditText>(R.id.TitleEdit)
        descriptionTxt = root.findViewById<EditText>(R.id.DescriptionEdit)
        tabIndicator = root.findViewById<TabLayout>(R.id.tabIndicator)

        if(editPostViewModel.title !== null){
            titleTxt.setText(editPostViewModel.title)
        }

        if(editPostViewModel.descripcion !== null){
            descriptionTxt.setText(editPostViewModel.descripcion)
        }

        if(editPostViewModel.imagesAnt != null && editPostViewModel.images == null){
            var listImages: MutableList<Bitmap> = mutableListOf()

            editPostViewModel!!.imagesAnt?.forEach { image ->
                val strImage:String =  image.replace("data:image/*;base64,","")
                val byteArray =  Base64.getDecoder().decode(strImage)

                if(byteArray != null){
                    listImages.add(ImageUtilities.getBitMapFromByteArray(byteArray))
                }
            }
            imageView.adapter = ImageAdapter(listImages)
            TabLayoutMediator(tabIndicator, imageView) { _, _ -> }.attach()
        }else{
            if(editPostViewModel.images != null){
                imageView.adapter = ImageAdapter(editPostViewModel.images!!.toList())
                TabLayoutMediator(tabIndicator, imageView) { _, _ -> }.attach()
            }
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
                try {
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

                        // Update the ViewPager2 adapter with the selected images
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
        if(p0!!.id == R.id.btnPostEPSig){
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
                editPostViewModel.title = title
                editPostViewModel.descripcion = descripcion
                if(selectedImages.isNotEmpty()){
                    editPostViewModel.images = selectedImages
                }

                findNavController().navigate(R.id.action_editPostFragment_to_editPostLastFragment)
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
            findNavController().navigate(R.id.action_editPostFragment_to_searchFragment)
        }
        if(p0!!.id == R.id.btnSubirFotoEdit){
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