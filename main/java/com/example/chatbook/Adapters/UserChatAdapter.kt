package com.example.chatbook.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatbook.ChatDetail
import com.example.chatbook.Models.UserChatsView
import com.example.chatbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class UserChatAdapter(var context: android.content.Context, private var pArrayList: ArrayList<UserChatsView>) : RecyclerView.Adapter<UserChatAdapter.MyViewHolder>() {
    val auth= FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_chats_sample, parent, false)


        return MyViewHolder(itemView/*, mListener*/)



    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem= pArrayList.get(position)
        holder.name.text= currentItem.firstname
        holder.lastMsg.text= currentItem.lastMessage
        Picasso.get().load(currentItem.url).placeholder(R.drawable.user).into(holder.image)



        ///////////For last message//////////
        val senderRoom= auth.currentUser!!.uid + currentItem.userID

        FirebaseDatabase.getInstance().getReference("Chats")
                .child(senderRoom)
                .orderByChild("timeStamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.hasChildren()){
                              for (i in snapshot.children){
                                  holder.lastMsg.text= i.child("messageText").getValue().toString()
                              }
                        }else{
                            holder.lastMsg.text= ""
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
//////////////////////last msg/////////////


    holder.nView.setOnClickListener(object : View.OnClickListener{
        override fun onClick(v: View?) {
            val intent = Intent(context, ChatDetail::class.java)
            intent.putExtra("imgUrl" , currentItem.url)
            intent.putExtra("userID" ,currentItem.userID)
            intent.putExtra("name" ,currentItem.firstname)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity (intent);
        }


    })

    }

    override fun getItemCount(): Int {
        return pArrayList.size
    }

    class MyViewHolder(itemView: View, /*listener: onItemClickListener*/): RecyclerView.ViewHolder(itemView){

        val image: ImageView = itemView.findViewById(R.id.friendImg)
        val name: TextView = itemView.findViewById(R.id.name)
        val lastMsg: TextView = itemView.findViewById(R.id.lastMsg)
        val nView: View= itemView

      /*  init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }*/

    }



}