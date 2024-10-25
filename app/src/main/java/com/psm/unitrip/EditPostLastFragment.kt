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

class EditPostLastFragment : Fragment(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_edit_post_last, container, false)
        val searchBtnDir = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtnDir.setOnClickListener(this)
        val btnCancelC = root.findViewById<Button>(R.id.btnCancelEPL)
        btnCancelC.setOnClickListener(this)
        val btnSaveC = root.findViewById<Button>(R.id.btnSaveEPL)
        btnSaveC.setOnClickListener(this)
        val backCreateBtn = root.findViewById<ImageButton>(R.id.backEditBtn)
        backCreateBtn.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.searchBtnDir){
            findNavController().navigate(R.id.action_editPostLastFragment_to_searchFragment)
        }
        if(p0!!.id == R.id.btnCancelEPL){
            findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
        }
        if(p0!!.id == R.id.btnSaveEPL){
            findNavController().navigate(R.id.action_editPostLastFragment_to_profileFragment)
        }

        if(p0!!.id == R.id.backEditBtn){
            findNavController().navigate(R.id.action_editPostLastFragment_to_editPostFragment)
        }
    }

}