package com.psm.unitrip.Requests

data class SendMsgRequest(
    val idSender: Int,
    val idChat: Int,
    val msg: String
)
