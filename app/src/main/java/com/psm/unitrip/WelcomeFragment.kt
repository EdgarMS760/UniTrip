package com.psm.unitrip

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class WelcomeFragment : Fragment(), OnClickListener {

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
        val root = inflater.inflate(R.layout.fragment_welcome, container, false)
        val LogInBtn =  root.findViewById<Button>(R.id.LogInBtn)
        LogInBtn.setOnClickListener(this)
        val signUpLinked =  root.findViewById<TextView>(R.id.signUpLinked)
        signUpLinked.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.LogInBtn->{
                this.listener?.moveNextPage(2)
            }
            R.id.signUpLinked-> {
                this.listener?.moveNextPage(3)
            }
        }
    }

}