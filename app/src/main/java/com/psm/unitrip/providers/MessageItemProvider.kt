package com.psm.unitrip.providers

import com.psm.unitrip.classes.MessageItem
import java.sql.Timestamp

class MessageItemProvider {
    companion object {
        val MessageItemList = listOf<MessageItem>(
            MessageItem(
                "Hola que tal",
                Timestamp(System.currentTimeMillis()),
                "gigakai"
            ),
            MessageItem(
                "Todo bien, ¿y tú?",
                Timestamp(System.currentTimeMillis() + 60000),
                "maiki"
            ),
            MessageItem(
                "Bien también, gracias.",
                Timestamp(System.currentTimeMillis() + 120000),
                "gigakai"
            ),
            MessageItem(
                "¿Qué haces?",
                Timestamp(System.currentTimeMillis() + 180000),
                "maiki"
            ),
            MessageItem(
                "Trabajando en un proyecto, ¿y tú?",
                Timestamp(System.currentTimeMillis() + 240000),
                "gigakai"
            ),
            MessageItem(
                "Descansando un poco.",
                Timestamp(System.currentTimeMillis() + 300000),
                "maiki"
            )
        )
    }
}