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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Manager.PostManager
import com.psm.unitrip.Manager.PreferenceManager
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Post
import com.psm.unitrip.Models.Usuario
import com.psm.unitrip.UserApplication.Companion.dbHelper
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.Utilities.NetworkUtils
import com.psm.unitrip.adapters.PostItemAdapter
import com.psm.unitrip.classes.CreatePostViewModel
import com.psm.unitrip.classes.EditPostViewModel
import com.psm.unitrip.classes.PostItem
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import com.psm.unitrip.providers.PostITemProvider
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ProfileFragment : Fragment(), OnClickListener {
    val createPostViewModel: CreatePostViewModel by activityViewModels()
    val editPostViewModel: EditPostViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View = inflater.inflate(R.layout.fragment_profile, container, false)

        val loadIcon = root.findViewById<CircularProgressIndicator>(R.id.loadPostProfileIndicator)

        loadIcon.visibility = View.VISIBLE

        val user: Usuario? = SessionManager.getUsuario()
        //Poner la informacion del Usuario

        val userPfp: ShapeableImageView = root.findViewById<ShapeableImageView>(R.id.userPfpAct)

        if(user !== null){
            val usernameView = root.findViewById<TextView>(R.id.usernameView)
            val nombreView = root.findViewById<TextView>(R.id.fullNameView)
            val nombreCompleto = user.nombre + " " + user.apellido

            val strImage:String =  user.profilePic.replace("data:image/*;base64,","")
            val byteArray =  Base64.getDecoder().decode(strImage)

            if(byteArray != null){
                userPfp.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
            }

            usernameView.setText(user.username)
            nombreView.setText(nombreCompleto)
        }


        val btnLogOut = root.findViewById<ImageButton>(R.id.logOutBtn)
        val btnEPfp = root.findViewById<ImageButton>(R.id.editPfpBtn)
        btnEPfp.setOnClickListener(this)
        btnLogOut.setOnClickListener(this)

        val postAdapter = PostItemAdapter(mutableListOf()) { postItem ->
            editPostViewModel.idPost = postItem.idPost
            editPostViewModel.title = postItem.title
            editPostViewModel.descripcion = postItem.description
            editPostViewModel.location = postItem.location
            editPostViewModel.price = postItem.precio
            editPostViewModel.status = postItem.status
            editPostViewModel.imagesAnt = postItem.arrayImagenes
            findNavController().navigate(R.id.action_profileFragment_to_editPostFragment)
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.RecyclerPostListProfile)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postAdapter



        if(NetworkUtils.isNetworkAvailable(requireContext())){
            val factory = ManagerFactory(RestEngine.getRestEngine())

            val postManager = factory.createManager(Post::class.java)

            if(SessionManager.getUsuario() != null){
                val user = SessionManager.getUsuario()
                (postManager as? PostManager)?.getMyPosts(user!!.idUsuario){posts->
                    loadIcon.visibility = View.GONE
                    if(posts != null){
                        postAdapter.updatePosts(posts)

                    }
                    val postsDrafts: MutableList<Post> = dbHelper.selectMisPosts(user!!.idUsuario)
                    postAdapter.addPosts(postsDrafts)
                }
            }
        }else{

            val posts: MutableList<Post> = dbHelper.selectMyPosts(user!!.idUsuario)
            postAdapter.updatePosts(posts)

            val postsDrafts: MutableList<Post> = dbHelper.selectMisPosts(user!!.idUsuario)
            postAdapter.addPosts(postsDrafts)
            loadIcon.visibility = View.GONE
        }

        editPostViewModel.reset()
        createPostViewModel.reset()

        return root
    }

    override fun onClick(p0: View?) {

        if(p0!!.id == R.id.logOutBtn){
            SessionManager.logOut(requireContext())
            val intent =  Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
        }

        if(p0!!.id == R.id.editPfpBtn){
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

    }

}