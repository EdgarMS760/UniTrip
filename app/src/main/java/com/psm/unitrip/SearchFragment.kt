package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.psm.unitrip.Manager.ChatManager
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Manager.PreferenceManager
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.adapters.PostItemAdapter
import com.psm.unitrip.classes.ChatViewModel
import com.psm.unitrip.classes.CreatePostViewModel
import com.psm.unitrip.classes.EditPostViewModel
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import com.psm.unitrip.providers.PostITemProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class SearchFragment : Fragment() {
    val createPostViewModel: CreatePostViewModel by activityViewModels()
    val editPostViewModel: EditPostViewModel by activityViewModels()
    val chatVM: ChatViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View = inflater.inflate(R.layout.fragment_search, container, false)

        val loadIcon = root.findViewById<CircularProgressIndicator>(R.id.loadSearchIndicator)

        loadIcon.visibility = View.VISIBLE

        val filterSpinner = root.findViewById<Spinner>(R.id.filterSpinner)
        val orderSpinner = root.findViewById<Spinner>(R.id.orderSpinner)

        val filterOptions = listOf("Filtrar Por", "Año 2024", "Por Año 2024")
        val orderOptions = listOf("Ordenar Por", "Por Año", "Por ID" , "Por Usuario")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            orderOptions
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        orderSpinner.adapter = adapter

        val adapterF = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            filterOptions
        )

        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        filterSpinner.adapter = adapterF


        val postAdapter = PostItemAdapter(mutableListOf()) { postItem ->

            if(NetworkUtils.isNetworkAvailable(requireContext())){
                val factoryChat = ManagerFactory(RestEngine.getRestEngine())

                val chatManager = factoryChat.createManager(Chat::class.java)

                if(chatManager != null){
                    (chatManager as? ChatManager)?.createChatPost(Chat(0, SessionManager.getUsuario()!!.idUsuario, postItem.idUsuario, "", "", "", "", "", "")){ chat ->
                        if(chat != null){
                            val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                            val currentDate = utcFormat.format(Date())

                            PreferenceManager.setLastSync(requireContext(), currentDate)
                            chatVM.chatAct = chat
                            findNavController().navigate(R.id.action_searchFragment_to_individualChatFragment)
                        }
                    }
                }
            }else{
                mostrarNoInternet()
            }
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerListSearch)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postAdapter




        if(NetworkUtils.isNetworkAvailable(requireContext())){
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
        }else{
            val posts: MutableList<Post> = UserApplication.dbHelper.selectPosts(SessionManager.getUsuario()!!.idUsuario)
            postAdapter.updatePosts(posts)
            loadIcon.visibility = View.GONE
        }

        editPostViewModel.reset()
        createPostViewModel.reset()

        return root
    }


}