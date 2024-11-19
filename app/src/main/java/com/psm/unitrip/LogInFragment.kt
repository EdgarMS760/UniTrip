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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.classes.SessionManager


class LogInFragment : Fragment(), OnClickListener {
    private var listener: OnFragmentWelcomeActionsListener? = null
    private lateinit var emailTxt: EditText
    private lateinit var passTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentWelcomeActionsListener) {
            this.listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_log_in, container, false)
        val backLogInBtn = root.findViewById<ImageButton>(R.id.backLogInBtn)
        backLogInBtn.setOnClickListener(this)
        val LogBtn = root.findViewById<Button>(R.id.LogBtn)
        LogBtn.setOnClickListener(this)
        val signUpViewLI = root.findViewById<TextView>(R.id.signUpViewLI)
        signUpViewLI.setOnClickListener(this)

        emailTxt = root.findViewById<EditText>(R.id.emailTxt)
        passTxt = root.findViewById<EditText>(R.id.passTxt)

        return root
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
        when (p0!!.id) {
            R.id.backLogInBtn -> {
                this.listener?.moveNextPage(1)
            }

            R.id.LogBtn -> {
                p0.animate()
                    .alpha(0.5f)
                    .setDuration(300)
                    .withEndAction {
                        p0.animate()
                            .alpha(1f)
                            .setDuration(300)
                    }
                if(NetworkUtils.isNetworkAvailable(requireContext())){
                    val email = emailTxt.text.toString()
                    val password = passTxt.text.toString()

                    SessionManager.logIn(email, password, requireContext()){ success ->
                        if(success){
                            val intent =  Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(activity?.applicationContext,"Fallo de Credenciales", Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    mostrarNoInternet()
                }

            }

            R.id.signUpViewLI -> {
                this.listener?.moveNextPage(3)
            }
        }
    }


}