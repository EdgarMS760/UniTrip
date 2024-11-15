package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.psm.unitrip.Manager.ChatManager
import com.psm.unitrip.Manager.ManagerFactory
import com.psm.unitrip.Models.Chat
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.Utilities.ImageUtilities
import com.psm.unitrip.adapters.MessageItemAdapter
import com.psm.unitrip.classes.ChatViewModel
import com.psm.unitrip.classes.RestEngine
import com.psm.unitrip.classes.SessionManager
import com.psm.unitrip.providers.MessageItemProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64

class IndividualChatFragment : Fragment(), OnClickListener {
    private lateinit var messageTxt: EditText
    private lateinit var adapterMsg: MessageItemAdapter
    private lateinit var recycler: RecyclerView
    val chatVM: ChatViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_individual_chat, container, false)

        val loadIcon = root.findViewById<CircularProgressIndicator>(R.id.messageLoadIndicator)
        loadIcon.visibility = View.VISIBLE

        if(chatVM.chatAct == null){
            findNavController().navigate(R.id.action_individualChatFragment_to_chatsFragment)
        }


        recycler = root.findViewById<RecyclerView>(R.id.recyclerMessages)
        recycler.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
        }
        adapterMsg = MessageItemAdapter(mutableListOf())
        recycler.adapter = adapterMsg


        val factory = ManagerFactory(RestEngine.getRestEngine())

        val chatManager = factory.createManager(Chat::class.java)

        (chatManager as? ChatManager)?.getMessages(chatVM.chatAct!!.idChat) { Mensajes ->
            if(Mensajes != null){
                loadIcon.visibility = View.GONE
                adapterMsg.updateMensajes(Mensajes)
                recycler.scrollToPosition(recycler.adapter?.itemCount?.minus(1) ?: 0)
            }
        }

        val nameUser = root.findViewById<TextView>(R.id.nameUserChat)
        val userImg = root.findViewById<ImageView>(R.id.LogIconContainer)


        if(SessionManager.getUsuario() != null){
            if(chatVM.chatAct?.idEmisor == SessionManager.getUsuario()?.idUsuario){
                nameUser.setText(chatVM.chatAct?.nombreReceptor)

                val strImage:String =  chatVM.chatAct?.fotoReceptor!!.replace("data:image/*;base64,","")
                val byteArray =  Base64.getDecoder().decode(strImage)

                if(byteArray != null){
                    userImg.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
                }
            }else{
                nameUser.setText(chatVM.chatAct?.nombreEmisor)

                val strImage:String =  chatVM.chatAct?.fotoEmisor!!.replace("data:image/*;base64,","")
                val byteArray =  Base64.getDecoder().decode(strImage)

                if(byteArray != null){
                    userImg.setImageBitmap(ImageUtilities.getBitMapFromByteArray(byteArray))
                }
            }

        }

        val btnBack = root.findViewById<ImageButton>(R.id.backChatsBtn)
        btnBack.setOnClickListener(this)

        val sendMsgBtn = root.findViewById<ImageButton>(R.id.sendMsgBtn)
        sendMsgBtn.setOnClickListener(this)

        messageTxt = root.findViewById<EditText>(R.id.msgEditTxt)

        return root
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == R.id.backChatsBtn){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            findNavController().navigate(R.id.action_individualChatFragment_to_chatsFragment)
        }

        if(p0!!.id == R.id.sendMsgBtn){
            p0.animate()
                .alpha(0.5f)
                .setDuration(300)
                .withEndAction {
                    p0.animate()
                        .alpha(1f)
                        .setDuration(300)
                }
            var isValid = true

            val msg = messageTxt.text.toString()

            if(msg.isEmpty()){
                isValid = false
            }

            if(isValid){
                messageTxt.setText("")

                val factory = ManagerFactory(RestEngine.getRestEngine())

                val chatManager = factory.createManager(Chat::class.java)

                (chatManager as? ChatManager)?.sendMsg(SessionManager.getUsuario()!!.idUsuario, chatVM.chatAct!!.idChat, msg) { success ->
                    if(success){
                        val now = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy")
                        val formattedDate = now.format(formatter)
                        adapterMsg.addMsg(Mensaje(SessionManager.getUsuario()!!.idUsuario, msg, chatVM.chatAct!!.idChat, formattedDate.toString()))
                        recycler.scrollToPosition(recycler.adapter?.itemCount?.minus(1) ?: 0)
                    }
                }


            }else{
                Toast.makeText(this.requireContext(), "No ingresaste ningun mensaje", Toast.LENGTH_SHORT).show()
            }
        }
    }


}