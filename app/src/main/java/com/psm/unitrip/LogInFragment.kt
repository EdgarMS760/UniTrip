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
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Manager.PreferenceManager
import com.psm.unitrip.Manager.UserManager
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class LogInFragment : Fragment(), OnClickListener {
    private var listener: OnFragmentWelcomeActionsListener? = null
    private lateinit var emailTxt: EditText
    private lateinit var passTxt: EditText
    private lateinit var loadIcon: CircularProgressIndicator

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

        loadIcon = root.findViewById<CircularProgressIndicator>(R.id.loadSyncIndicator)

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
                p0.animate()
                    .alpha(0.5f)
                    .setDuration(300)
                    .withEndAction {
                        p0.animate()
                            .alpha(1f)
                            .setDuration(300)
                    }
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
                    loadIcon.visibility = View.VISIBLE
                    SessionManager.logIn(email, password, requireContext()){ success ->
                        if(success){
                            val factory = ManagerFactory(RestEngine.getRestEngine())
                            val userManager = factory.createManager(Usuario::class.java)

                            if(PreferenceManager.hasLastSync(requireContext())){

                                val fechaSync = PreferenceManager.getLastSync(requireContext())
                                (userManager as? UserManager)?.sync(fechaSync.toString()) { state ->
                                    if(state){
                                        (userManager as? UserManager)?.syncUpdated(fechaSync.toString()) { synced ->
                                            val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                                timeZone = TimeZone.getTimeZone("UTC")
                                            }
                                            val currentDate = utcFormat.format(Date())
                                            loadIcon.visibility = View.GONE

                                            PreferenceManager.setLastSync(requireContext(), currentDate)
                                            val intent =  Intent(requireContext(), MainActivity::class.java).apply {
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            }
                                            startActivity(intent)
                                        }
                                    }else{
                                        loadIcon.visibility = View.GONE

                                        val intent =  Intent(requireContext(), MainActivity::class.java).apply {
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        }
                                        startActivity(intent)
                                    }
                                }

                            }else{
                                (userManager as? UserManager)?.sync("1970-01-01 00:00:00") { state ->
                                    if(state){
                                        val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                            timeZone = TimeZone.getTimeZone("UTC")
                                        }
                                        val currentDate = utcFormat.format(Date())
                                        loadIcon.visibility = View.GONE

                                        PreferenceManager.setLastSync(requireContext(), currentDate)
                                        val intent =  Intent(requireContext(), MainActivity::class.java).apply {
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        }
                                        startActivity(intent)
                                    }else{
                                        loadIcon.visibility = View.GONE
                                        val intent =  Intent(requireContext(), MainActivity::class.java).apply {
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        }
                                        startActivity(intent)
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(activity?.applicationContext,"Fallo de Credenciales", Toast.LENGTH_LONG).show()
                            loadIcon.visibility = View.GONE
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