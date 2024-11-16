package com.psm.unitrip

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.psm.unitrip.classes.SessionManager
import com.psm.unitrip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up BottomNavigationView
        val bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // Optional: Add a listener for navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val idCurrent = navController.currentDestination?.id
            when (item.itemId) {
                R.id.home -> {
                    when (idCurrent){
                        R.id.postFragment->{
                            navController.navigate(R.id.action_postFragment_to_landingFragment)
                        }
                        R.id.chatsFragment->{
                            navController.navigate(R.id.action_chatsFragment_to_landingFragment)
                        }
                        R.id.profileFragment->{
                            navController.navigate(R.id.action_profileFragment_to_landingFragment)
                        }
                        R.id.editProfileFragment->{
                            navController.navigate(R.id.action_editProfileFragment_to_landingFragment)
                        }
                        R.id.searchFragment->{
                            navController.navigate(R.id.action_searchFragment_to_landingFragment)
                        }
                        R.id.individualChatFragment->{
                            navController.navigate(R.id.action_individualChatFragment_to_landingFragment)
                        }
                        R.id.postLastFragment->{
                            navController.navigate(R.id.action_postLastFragment_to_landingFragment)
                        }
                        R.id.editPostFragment->{
                            navController.navigate(R.id.action_editPostFragment_to_landingFragment)
                        }
                        R.id.editPostLastFragment->{
                            navController.navigate(R.id.action_editPostLastFragment_to_landingFragment)
                        }
                    }

                    true
                }
                R.id.chatsLay -> {
                    when (idCurrent){
                        R.id.postFragment->{
                            navController.navigate(R.id.action_postFragment_to_chatsFragment)

                        }
                        R.id.landingFragment->{
                            navController.navigate(R.id.action_landingFragment_to_chatsFragment)

                        }
                        R.id.profileFragment->{
                            navController.navigate(R.id.action_profileFragment_to_chatsFragment)

                        }
                        R.id.editProfileFragment->{
                            navController.navigate(R.id.action_editProfileFragment_to_chatsFragment)
                        }
                        R.id.searchFragment->{
                            navController.navigate(R.id.action_searchFragment_to_chatsFragment)
                        }
                        R.id.individualChatFragment->{
                            navController.navigate(R.id.action_individualChatFragment_to_chatsFragment)
                        }
                        R.id.postLastFragment->{
                            navController.navigate(R.id.action_postLastFragment_to_chatsFragment)
                        }
                        R.id.editPostFragment->{
                            navController.navigate(R.id.action_editPostFragment_to_chatsFragment)
                        }
                        R.id.editPostLastFragment->{
                            navController.navigate(R.id.action_editPostLastFragment_to_chatsFragment)
                        }
                    }
                    true
                }
                R.id.profile -> {
                    when (idCurrent){
                        R.id.postFragment->{
                            navController.navigate(R.id.action_postFragment_to_profileFragment)

                        }
                        R.id.chatsFragment->{
                            navController.navigate(R.id.action_chatsFragment_to_profileFragment)

                        }
                        R.id.landingFragment->{
                            navController.navigate(R.id.action_landingFragment_to_profileFragment)

                        }
                        R.id.editProfileFragment->{
                            navController.navigate(R.id.action_editProfileFragment_to_profileFragment)
                        }
                        R.id.searchFragment->{
                            navController.navigate(R.id.action_searchFragment_to_editProfileFragment)
                        }
                        R.id.individualChatFragment->{
                            navController.navigate(R.id.action_individualChatFragment_to_profileFragment)
                        }
                        R.id.postLastFragment->{
                            navController.navigate(R.id.action_postLastFragment_to_profileFragment)
                        }
                        R.id.editPostFragment->{
                            navController.navigate(R.id.action_editPostFragment_to_profileFragment)
                        }
                        R.id.editPostLastFragment->{
                            navController.navigate(R.id.action_editPostLastFragment_to_profileFragment)
                        }
                    }
                    true
                }
                R.id.addPost -> {
                    when (idCurrent){
                        R.id.landingFragment->{
                            navController.navigate(R.id.action_landingFragment_to_postFragment)

                        }
                        R.id.chatsFragment->{
                            navController.navigate(R.id.action_chatsFragment_to_postFragment)

                        }
                        R.id.profileFragment->{
                            navController.navigate(R.id.action_profileFragment_to_postFragment)

                        }
                        R.id.editProfileFragment->{
                            navController.navigate(R.id.action_editProfileFragment_to_postFragment)
                        }
                        R.id.searchFragment->{
                            navController.navigate(R.id.action_searchFragment_to_postFragment)
                        }
                        R.id.individualChatFragment->{
                            navController.navigate(R.id.action_individualChatFragment_to_postFragment)
                        }
                        R.id.postLastFragment->{
                            navController.navigate(R.id.action_postLastFragment_to_postFragment)
                        }
                        R.id.editPostFragment->{
                            navController.navigate(R.id.action_editPostFragment_to_postFragment)
                        }
                        R.id.editPostLastFragment->{
                            navController.navigate(R.id.action_editPostLastFragment_to_postFragment)
                        }
                    }
                    true
                }
                else -> false
            }
        }

    }
}