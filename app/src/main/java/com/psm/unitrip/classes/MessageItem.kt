package com.psm.unitrip.classes

import java.sql.Timestamp

data class MessageItem(val MsgTxt: String, val timeLastMsg: Timestamp, val sender: String) {
}