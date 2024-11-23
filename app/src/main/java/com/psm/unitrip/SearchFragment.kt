package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
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
    private lateinit var searchView: SearchView
    private lateinit var postAdapter: PostItemAdapter
    private var lastClickTime = 0L
    private var postsList: List<Post> = emptyList()
    private var selectedOrder = "Ordenar Por"

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

        searchView = root.findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterPosts(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterPosts(newText.toString())
                return true
            }
        })


        loadIcon.visibility = View.VISIBLE

        val orderSpinner = root.findViewById<Spinner>(R.id.orderSpinner)

        val orderOptions = listOf("Ordenar Por", "Por Precio", "Por ID" , "Por Usuario")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            orderOptions
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        orderSpinner.adapter = adapter



        postAdapter = PostItemAdapter(mutableListOf()) { postItem ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime

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
                        postsList = posts
                        postAdapter.updatePosts(posts)
                    }
                }
            }
        }else{
            val posts: MutableList<Post> = UserApplication.dbHelper.selectPosts(SessionManager.getUsuario()!!.idUsuario)
            postAdapter.updatePosts(posts)
            postsList = posts
            loadIcon.visibility = View.GONE
        }


        orderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedOrder = orderOptions[position]
                applyOrderToPosts(postAdapter.postList)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }


        editPostViewModel.reset()
        createPostViewModel.reset()

        return root
    }

    fun filterPosts(query: String){

        val filteredPostss = if (!query.isNullOrEmpty()) {
            postsList.filter { post ->
                post.title.contains(query, ignoreCase = true) || post.description.contains(query, ignoreCase = true)
            }
        } else {
            postsList
        }

        applyOrderToPosts(filteredPostss)
    }

    fun applyOrderToPosts(filteredPosts: List<Post> = postsList) {
        val sortedPosts = when (selectedOrder) {
            "Por Precio" -> filteredPosts.sortedBy { it.precio.toDouble() }
            "Por ID" -> filteredPosts.sortedBy { it.idPost }
            "Por Usuario" -> filteredPosts.sortedBy { it.username }
            else -> filteredPosts
        }

        postAdapter.updatePosts(sortedPosts)
    }

}