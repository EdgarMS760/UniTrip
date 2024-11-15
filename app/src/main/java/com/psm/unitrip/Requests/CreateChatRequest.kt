package com.psm.unitrip.Requests

data class CreateChatRequest (
    val idSender: Int,
    val idReceiver: Int
)