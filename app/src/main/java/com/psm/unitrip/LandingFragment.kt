package com.psm.unitrip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.psm.unitrip.Manager.ChatManager
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Post
import com.psm.unitrip.adapters.ChatItemAdapter
import com.psm.unitrip.adapters.PostItemAdapter
import com.psm.unitrip.classes.ChatViewModel
import com.psm.unitrip.classes.CreatePostViewModel
import com.psm.unitrip.classes.EditPostViewModel
import com.psm.unitrip.classes.PostItem
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import com.psm.unitrip.providers.ChatItemProvider
import com.psm.unitrip.providers.PostITemProvider

class LandingFragment : Fragment(), OnClickListener {
    val createPostViewModel: CreatePostViewModel by activityViewModels()
    val editPostViewModel: EditPostViewModel by activityViewModels()
    val chatVM: ChatViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = UserApplication.dbHelper.writableDatabase

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editPostViewModel.reset()
        createPostViewModel.reset()
        val root = inflater.inflate(R.layout.fragment_landing, container, false)

        val loadIcon = root.findViewById<CircularProgressIndicator>(R.id.loadPostIndicator)

        loadIcon.visibility = View.VISIBLE

        val searchBtn = root.findViewById<ImageButton>(R.id.searchBtnDir)
        searchBtn.setOnClickListener(this)


        val postAdapter = PostItemAdapter(emptyList()) { postItem ->

            val factoryChat = ManagerFactory(RestEngine.getRestEngine())

            val chatManager = factoryChat.createManager(Chat::class.java)

            if(chatManager != null){
                (chatManager as? ChatManager)?.createChatPost(Chat(0, SessionManager.getUsuario()!!.idUsuario, postItem.idUsuario, "", "", "", "", "", "")){ chat ->
                    if(chat != null){
                        chatVM.chatAct = chat
                        findNavController().navigate(R.id.action_landingFragment_to_individualChatFragment)
                    }
                }
            }



        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.RecyclerPostList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postAdapter

        val factory = ManagerFactory(RestEngine.getRestEngine())

        val postManager = factory.createManager(Post::class.java)

        if(SessionManager.getUsuario() != null){
            val user = SessionManager.getUsuario()
            postManager?.getAll(user!!.idUsuario){posts->
                loadIcon.visibility = View.GONE
                if(posts != null){
                    postAdapter.updatePosts(posts)
                }
            }
        }

        return root
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.searchBtnDir){
            findNavController().navigate(R.id.action_landingFragment_self)
        }
    }

}