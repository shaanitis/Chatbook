package com.example.chatbook.Adapters

import android.media.CamcorderProfile.get
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbook.Models.Messages
import com.example.chatbook.Models.UserChatsView
import com.example.chatbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.reflect.Array.get
import java.nio.file.Paths.get


private const val SENDER_VIEW_TYPE: Int= 1
private const val RECIEVER_VIEW_TYPE: Int= 2
class MsgAdapter(var context: android.content.Context, private var chatList: ArrayList<Messages>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    val auth= FirebaseAuth.getInstance()

    class RecieverViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val recieverText: TextView = itemView.findViewById(R.id.recieverText)
        val recieverTime: TextView = itemView.findViewById(R.id.recieverTime)




    }
    class SenderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val senderText: TextView = itemView.findViewById(R.id.senderText)
        val senderTime: TextView = itemView.findViewById(R.id.senderTime)




    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType== SENDER_VIEW_TYPE){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_sender, parent, false)
           return SenderViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_reciever, parent, false)
            return RecieverViewHolder(view)
        }
    }





    override fun getItemCount(): Int {
       return chatList.size
    }
    override fun getItemViewType(position: Int): Int {
        val currentItem = chatList.get(position)
       if (currentItem.userID.equals(auth.currentUser!!.uid.toString())) {
           return SENDER_VIEW_TYPE
        } else if (auth.currentUser!!.uid.toString()!= currentItem.recieverID)  {
              return RECIEVER_VIEW_TYPE
        }  else
            return RECIEVER_VIEW_TYPE

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem= chatList.get(position)
        if( getItemViewType(position)== SENDER_VIEW_TYPE){
            (holder as SenderViewHolder)
            holder.senderText.text= currentItem.messageText
            holder.senderTime.text= currentItem.timeStamp.toString()

        }
        else if (getItemViewType(position)== RECIEVER_VIEW_TYPE){
            (holder as RecieverViewHolder)
        holder.recieverText.text= currentItem.messageText
            holder.recieverTime.text= currentItem.timeStamp.toString()
        }
    }


}