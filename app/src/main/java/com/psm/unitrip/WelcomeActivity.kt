package com.psm.unitrip

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.psm.unitrip.Manager.PreferenceManager
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.classes.SessionManager
import com.psm.unitrip.databinding.ActivityWelcomeBinding

class WelcomeActivity: AppCompatActivity(), OnFragmentWelcomeActionsListener {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        SessionManager.loadSession(this)
        if(SessionManager.getIsLoggedIn()){


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(WelcomeFragment())
    }


    private fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.welcomeContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.welcomeContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun moveNextPage(sigPage: Int) {
        when(sigPage){
            1->{
                replaceFragment(WelcomeFragment())
            }
            2-> {
                replaceFragment(LogInFragment())
            }
            3-> {
                replaceFragment(SignUpFragment())
            }
            4-> {
                replaceFragment(SignUpMid())
            }
            5-> {
                replaceFragment(SignUpLast())
            }
        }
    }
}