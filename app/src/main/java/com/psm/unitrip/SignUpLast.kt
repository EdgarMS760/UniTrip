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
import android.widget.ImageButton
import android.widget.TextView

class SignUpLast : Fragment(), OnClickListener {
    private var listener: OnFragmentWelcomeActionsListener? = null

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
        return root
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.backLastBtn->{
                this.listener?.moveNextPage(4)
            }
            R.id.registerBtn->{
                val intent =  Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
            R.id.signUpViewLast-> {
                this.listener?.moveNextPage(2)
            }
        }
    }


}