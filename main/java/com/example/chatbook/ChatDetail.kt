package com.example.chatbook

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbook.Adapters.MsgAdapter
import com.example.chatbook.Adapters.UserChatAdapter
import com.example.chatbook.Models.Messages
import com.example.chatbook.Models.UserChatsView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.datepicker.DateValidatorPointForward.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.Instant.now
import java.time.LocalDateTime.now
import java.time.MonthDay.now
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_INSTANT
import java.util.*
import kotlin.collections.ArrayList

class ChatDetail : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        this.supportActionBar?.hide()

        val friendImg: ImageView= findViewById(R.id.friendImg)
        val friendImgUrl: String= intent.getStringExtra("imgUrl").toString()
        Picasso.get().load(friendImgUrl).placeholder(R.drawable.user).into(friendImg)


        val auth: FirebaseAuth= FirebaseAuth.getInstance()
        val database= FirebaseDatabase.getInstance()

        val senderId= auth.currentUser!!.uid.toString()
        val recieverID= intent.getStringExtra("userID")
        val name= intent.getStringExtra("name")

        val userName: TextView= findViewById(R.id.textView2)
        userName.text= name


        lateinit var msgList: ArrayList<Messages>
        var msgRView: RecyclerView= findViewById(R.id.MsgRView)
        msgList= arrayListOf<Messages>()

        msgRView.layoutManager= LinearLayoutManager(applicationContext)
        msgRView.setHasFixedSize(true)

        var adapter = MsgAdapter(applicationContext, msgList)
        msgRView.adapter = adapter

        /*msgList.clear()*/
        val senderRoom= senderId+ recieverID
        val recieverRoom= recieverID+ senderId

        val send: ImageButton= findViewById(R.id.send)


        FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for (i:DataSnapshot in  snapshot.children){
                    val msg: String=i.child("messageText").getValue().toString()
                    val userId: String=i.child("userID").getValue().toString()
                    val timee: String= i.child("timeStamp").getValue().toString()
                 val recieverIde= recieverID
                   msgList.add(Messages(userId, msg, timee,recieverIde))

                }


                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




        send.setOnClickListener(){







            val editText: EditText= findViewById(R.id.editText)
            val et: String?=  editText.text.toString()
            val currentUserId: String= auth.currentUser?.uid.toString()

            val calendar: Calendar= Calendar.getInstance()
            val format : SimpleDateFormat= SimpleDateFormat("HH:mm")
            val time: String = format.format(calendar.time)
            val recieverId:String= recieverID.toString()

            val model: Messages= Messages(currentUserId, et, time, recieverId)

            editText.setText("")

            val myRef= FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom).push().setValue(model).addOnSuccessListener(OnSuccessListener{

  FirebaseDatabase.getInstance().getReference("Chats").child(recieverRoom).push().setValue(model).addOnSuccessListener(OnSuccessListener {  })




            })











    }
}
}