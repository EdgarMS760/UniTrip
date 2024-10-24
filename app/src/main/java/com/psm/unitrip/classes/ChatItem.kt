package com.psm.unitrip.classes

import java.sql.Timestamp

data class ChatItem(val name: String, val lastMsg: String, val photoPfp: String, val timeLastMsg: Timestamp) {

}