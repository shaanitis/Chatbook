package com.example.chatbook.Models
import android.graphics.Bitmap

data class UserChatsView(var firstname: String?, var url:String?, var lastMessage:String?, var userID:String?){
    constructor(): this("", "", "", ""
    )
}
