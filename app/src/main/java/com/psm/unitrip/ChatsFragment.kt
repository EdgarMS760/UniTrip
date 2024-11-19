package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.UserApplication.Companion.dbHelper
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.adapters.ChatItemAdapter
import com.psm.unitrip.classes.ChatViewModel
import com.psm.unitrip.classes.CreatePostViewModel
import com.psm.unitrip.classes.EditPostViewModel
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import com.psm.unitrip.providers.ChatItemProvider

class ChatsFragment : Fragment(), OnClickListener {
    val createPostViewModel: CreatePostViewModel by activityViewModels()
    val editPostViewModel: EditPostViewModel by activityViewModels()
    val chatVM: ChatViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chats, container, false)

        val loadIcon = root.findViewById<CircularProgressIndicator>(R.id.progressIndicator)

        loadIcon.visibility = View.VISIBLE



        val chatAdapter = ChatItemAdapter(emptyList()) { Chat ->
            chatVM.chatAct = Chat
            findNavController().navigate(R.id.action_chatsFragment_to_individualChatFragment)
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.RecyclerChatList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = chatAdapter


        if(NetworkUtils.isNetworkAvailable(requireContext())){
            val factory = ManagerFactory(RestEngine.getRestEngine())

            val chatManager = factory.createManager(Chat::class.java)

            if(SessionManager.getUsuario() != null){
                val user = SessionManager.getUsuario()
                chatManager?.getAll(user!!.idUsuario){chats->
                    loadIcon.visibility = View.GONE
                    if(chats != null){
                        chatAdapter.updateChats(chats)
                    }
                }
            }

        }else{
            val user = SessionManager.getUsuario()
            val listaChats: List<Chat> = dbHelper.selectChats(user!!.idUsuario)
            chatAdapter.updateChats(listaChats)
            loadIcon.visibility = View.GONE
        }



        val searchBtn = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtn.setOnClickListener(this)



        editPostViewModel.reset()
        createPostViewModel.reset()

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