package com.psm.unitrip.adapters

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.Models.Mensaje
import com.psm.unitrip.R
import com.psm.unitrip.classes.MessageItem
import com.psm.unitrip.classes.SessionManager
import java.text.SimpleDateFormat
import java.util.Locale

class MessageItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val msgContent = view.findViewById<TextView>(R.id.msgTxtItem)
    val msgDate = view.findViewById<TextView>(R.id.dateTxtItem)
    val containerMsg = view.findViewById<LinearLayout>(R.id.containerMsg)
    val colorTotal = view.findViewById<LinearLayout>(R.id.colorableMsg)

    fun render(messageItemModel: Mensaje) {
        msgContent.setText(messageItemModel.mensaje)


        msgDate.setText(messageItemModel.fechaCreacion)
        if (messageItemModel.idEmisor == SessionManager.getUsuario()?.idUsuario) {
            msgContent.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            msgDate.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            colorTotal.setBackgroundResource(R.drawable.message_own)
            containerMsg.gravity = Gravity.END
        } else {
            msgContent.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            msgDate.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            colorTotal.setBackgroundResource(R.drawable.message_external)
            containerMsg.gravity = Gravity.START

        }
    }
}