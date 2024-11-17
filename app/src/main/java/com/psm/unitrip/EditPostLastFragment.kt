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
import com.psm.unitrip.classes.EditPostViewModel

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

            findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
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

                findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
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
                findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
            }else{
                Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
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