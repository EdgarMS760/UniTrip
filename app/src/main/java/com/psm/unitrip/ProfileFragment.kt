package com.psm.unitrip

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.adapters.PostItemAdapter
import com.psm.unitrip.classes.PostItem
import com.psm.unitrip.providers.PostITemProvider

class ProfileFragment : Fragment(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View = inflater.inflate(R.layout.fragment_profile, container, false)
        val btnLogOut = root.findViewById<ImageButton>(R.id.logOutBtn)
        val btnEPfp = root.findViewById<ImageButton>(R.id.editPfpBtn)
        btnEPfp.setOnClickListener(this)
        btnLogOut.setOnClickListener(this)

        val postAdapter = PostItemAdapter(PostITemProvider.PostList) { postItem ->
            findNavController().navigate(R.id.action_profileFragment_to_editPostFragment)
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.RecyclerPostListProfile)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postAdapter
        return root
    }

    override fun onClick(p0: View?) {

        if(p0!!.id == R.id.logOutBtn){
            val intent =  Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
        }

        if(p0!!.id == R.id.editPfpBtn){
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

    }

}