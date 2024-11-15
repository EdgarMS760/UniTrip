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
import com.psm.unitrip.classes.CreatePostViewModel

class PostLastFragment : Fragment(), OnClickListener {
    private lateinit var locationTxt: EditText
    private lateinit var priceTxt: EditText
    val createPostViewModel: CreatePostViewModel by activityViewModels()

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
        val btnPostC = root.findViewById<Button>(R.id.btnPostC)
        btnPostC.setOnClickListener(this)
        val backCreateBtn = root.findViewById<ImageButton>(R.id.backCreateBtn)
        backCreateBtn.setOnClickListener(this)

        val locationBtnGet = root.findViewById<Button>(R.id.locationBtnGet)
        locationBtnGet.setOnClickListener(this)

        priceTxt = root.findViewById<EditText>(R.id.ETPriceCreatePost)
        locationTxt = root.findViewById<EditText>(R.id.ETLocationCreate)

        return root
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
            findNavController().navigate(R.id.action_postLastFragment_to_searchFragment)
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
            findNavController().navigate(R.id.action_postLastFragment_to_profileFragment)
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

            var isValid = true
            val regexPrice = Regex("^[0-9]+([.,][0-9]{1,2})?\$")

            val locationStr = locationTxt.text.toString()
            val price = priceTxt.text.toString()

            if(!regexPrice.matches(price)){
                isValid = false
                priceTxt.setBackgroundResource(R.drawable.input_sytle_error)
            }else{
                priceTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(locationStr.isEmpty()){
                isValid = false
                locationTxt.setBackgroundResource(R.drawable.input_sytle_error)
            }else{
                locationTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(isValid){

                findNavController().navigate(R.id.action_postLastFragment_to_profileFragment)
            }else{
                Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
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

            var isValid = true
            val regexPrice = Regex("^[0-9]+([.,][0-9]{1,2})?\$")

            val locationStr = locationTxt.text.toString()
            val price = priceTxt.text.toString()

            if(!regexPrice.matches(price)){
                isValid = false
                priceTxt.setBackgroundResource(R.drawable.input_sytle_error)
            }else{
                priceTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(locationStr.isEmpty()){
                isValid = false
                locationTxt.setBackgroundResource(R.drawable.input_sytle_error)
            }else{
                locationTxt.setBackgroundResource(R.drawable.input_style)
            }

            if(isValid){
                findNavController().navigate(R.id.action_postLastFragment_to_profileFragment)
            }else{
                Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
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
            findNavController().navigate(R.id.action_postLastFragment_to_postFragment)
        }

        if(p0!!.id == R.id.locationBtnGet){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }

        }

    }

}