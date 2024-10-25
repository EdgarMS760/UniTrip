package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.adapters.ChatItemAdapter
import com.psm.unitrip.providers.ChatItemProvider

class ChatsFragment : Fragment(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chats, container, false)
        val chatAdapter = ChatItemAdapter(ChatItemProvider.ChatItemList) { chatItem ->
            findNavController().navigate(R.id.action_chatsFragment_to_individualChatFragment)
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.RecyclerChatList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = chatAdapter
        val searchBtn = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtn.setOnClickListener(this)
        return root
    }

    fun initRecyclerView(){

    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.searchBtnDir){
            findNavController().navigate(R.id.action_chatsFragment_to_searchFragment)
        }
    }


}