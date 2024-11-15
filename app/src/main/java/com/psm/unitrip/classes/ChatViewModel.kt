package com.psm.unitrip.classes

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.psm.unitrip.Models.Chat

class ChatViewModel: ViewModel() {
    var chatAct: Chat? = null

    fun reset(){
        chatAct = null
    }
}