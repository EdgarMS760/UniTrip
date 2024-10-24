package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.adapters.MessageItemAdapter
import com.psm.unitrip.providers.MessageItemProvider

class IndividualChatFragment : Fragment(), OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_individual_chat, container, false)
        val recycler = root.findViewById<RecyclerView>(R.id.recyclerMessages)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = MessageItemAdapter(MessageItemProvider.MessageItemList)
        val btnBack = root.findViewById<ImageButton>(R.id.backChatsBtn)
        btnBack.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.backChatsBtn){
            findNavController().navigate(R.id.action_individualChatFragment_to_chatsFragment)
        }
    }


}