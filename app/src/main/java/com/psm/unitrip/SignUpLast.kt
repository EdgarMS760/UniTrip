package com.psm.unitrip

import android.content.Context
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
import android.widget.TextView
import android.widget.Toast

class SignUpLast : Fragment(), OnClickListener {
    private var listener: OnFragmentWelcomeActionsListener? = null
    private lateinit var usernameTxt: EditText
    private lateinit var phoneTxt: EditText
    private lateinit var direccionTxt: EditText

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
        val signUpViewLast =  root.findViewById<TextView>(R.id.signUpViewLast)
        signUpViewLast.setOnClickListener(this)

        usernameTxt = root.findViewById<EditText>(R.id.usernameTxtSU)
        phoneTxt = root.findViewById<EditText>(R.id.phoneTxt)
        direccionTxt = root.findViewById<EditText>(R.id.addressTxt)

        return root
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
                    val intent =  Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
                }


            }
            R.id.signUpViewLast-> {
                this.listener?.moveNextPage(2)
            }
        }
    }


}