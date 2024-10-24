package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

class EditProfileFragment : Fragment(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val btnCancel = root.findViewById<Button>(R.id.btnCancelEdit)
        val btnSave = root.findViewById<Button>(R.id.btnSaveEdit)
        btnCancel.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.btnCancelEdit){
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
        if(p0!!.id == R.id.btnSaveEdit){
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
    }

}