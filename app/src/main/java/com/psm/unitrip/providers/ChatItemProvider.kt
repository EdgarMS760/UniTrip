package com.psm.unitrip.providers

import com.psm.unitrip.classes.ChatItem
import java.sql.Timestamp

class ChatItemProvider {
    companion object {
        val ChatItemList = listOf<ChatItem>(
            ChatItem(
                "Gustavo Gomez",
                "Hola que tal",
                "@drawable/baseline_account_circle_24",
                Timestamp(System.currentTimeMillis())
            ),
            ChatItem(
                "Maria Lopez",
                "¿Cómo estás?",
                "@drawable/baseline_account_circle_24",
                Timestamp(System.currentTimeMillis())
            ),
            ChatItem(
                "Carlos Perez",
                "Nos vemos luego",
                "@drawable/baseline_account_circle_24",
                Timestamp(System.currentTimeMillis())
            ),
            ChatItem(
                "Ana Torres",
                "Ya llegué",
                "@drawable/baseline_account_circle_24",
                Timestamp(System.currentTimeMillis())
            ),
            ChatItem(
                "Luis Rodriguez",
                "¿Qué tal la reunión?",
                "@drawable/baseline_account_circle_24",
                Timestamp(System.currentTimeMillis())
            )
        )
    }
}