package com.psm.unitrip

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class SignUpMid : Fragment(), OnClickListener {
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
        val root = inflater.inflate(R.layout.fragment_sign_up_mid, container, false)
        val backStartBtn =  root.findViewById<ImageButton>(R.id.backStartBtn)
        backStartBtn.setOnClickListener(this)
        val nextMidBtn =  root.findViewById<Button>(R.id.nextMidBtn)
        nextMidBtn.setOnClickListener(this)
        val signUpViewMid =  root.findViewById<TextView>(R.id.signUpViewMid)
        signUpViewMid.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.backStartBtn->{
                this.listener?.moveNextPage(3)
            }
            R.id.nextMidBtn->{
                this.listener?.moveNextPage(5)
            }
            R.id.signUpViewMid-> {
                this.listener?.moveNextPage(2)
            }
        }
    }

}