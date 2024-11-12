package com.psm.unitrip

import android.content.Context
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

class SignUpMid : Fragment(), OnClickListener {
    private var listener: OnFragmentWelcomeActionsListener? = null
    private lateinit var passwordTxt: EditText
    private lateinit var firstNameTxt: EditText
    private lateinit var lastNameTxt: EditText

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
        val root = inflater.inflate(R.layout.fragment_sign_up_mid, container, false)
        val backStartBtn =  root.findViewById<ImageButton>(R.id.backStartBtn)
        backStartBtn.setOnClickListener(this)
        val nextMidBtn =  root.findViewById<Button>(R.id.nextMidBtn)
        nextMidBtn.setOnClickListener(this)
        val signUpViewMid =  root.findViewById<TextView>(R.id.signUpViewMid)
        signUpViewMid.setOnClickListener(this)


        firstNameTxt = root.findViewById<EditText>(R.id.lastNameTxt)
        lastNameTxt = root.findViewById<EditText>(R.id.nameTxt)
        passwordTxt = root.findViewById<EditText>(R.id.passTxtMid)


        return root
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.backStartBtn->{
                this.listener?.moveNextPage(3)
            }
            R.id.nextMidBtn->{
                p0.animate()
                    .alpha(0.5f)
                    .setDuration(300)
                    .withEndAction {
                        p0.animate()
                            .alpha(1f)
                            .setDuration(300)
                    }

                var isValid = true
                val regexPassword = Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[(!”#\$%&\\/=?¡¿:;,._+*{})]).{8,50}\$")
                val regexName = Regex("^([A-Za-zÑñÁáÉéÍíÓóÚú]+[\\'\\-]?[A-Za-zÑñÁáÉéÍíÓóÚú]+)(\\s+([A-Za-zÑñÁáÉéÍíÓóÚú]+[\\'\\-]?[A-Za-zÑñÁáÉéÍíÓóÚú]+))*\$")

                val password = passwordTxt.text.toString()
                val firstName = firstNameTxt.text.toString()
                val lastName = lastNameTxt.text.toString()


                if(!regexPassword.matches(password)){
                    isValid = false
                    passwordTxt.setBackgroundResource(R.drawable.input_sytle_error)
                }else{
                    passwordTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(!regexName.matches(firstName)){
                    isValid = false
                    firstNameTxt.setBackgroundResource(R.drawable.input_sytle_error)
                }else{
                    firstNameTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(!regexName.matches(lastName)){
                    isValid = false
                    lastNameTxt.setBackgroundResource(R.drawable.input_sytle_error)
                }else{
                    lastNameTxt.setBackgroundResource(R.drawable.input_style)
                }

                if(isValid){
                    this.listener?.moveNextPage(5)
                }else{
                    Toast.makeText(this.requireContext(), "Parametros Invalidos", Toast.LENGTH_SHORT).show()
                }


            }
            R.id.signUpViewMid-> {
                this.listener?.moveNextPage(2)
            }
        }
    }

}