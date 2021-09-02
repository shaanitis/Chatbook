package com.example.chatbook.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbook.Adapters.UserChatAdapter
import com.example.chatbook.Models.UserChatsView
import com.example.chatbook.R
import com.example.chatbook.Models.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ChatsFragment : Fragment() {


    val auth= FirebaseAuth.getInstance()
   lateinit  var chatsView: RecyclerView
    lateinit var userArrayList: ArrayList<UserChatsView>




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view: View= inflater.inflate(R.layout.fragment_chats, container, false)
        chatsView= view.findViewById(R.id.chatsView)
         userArrayList= arrayListOf<UserChatsView>()
        val database: DatabaseReference

        chatsView.layoutManager= LinearLayoutManager(context)
        chatsView.setHasFixedSize(true)

          userArrayList.clear()



             val myRef= FirebaseDatabase.getInstance().getReference("Users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {

                    if (i.key.toString()==auth.currentUser?.uid.toString()){

                    } else {
                        val imgUrl = i.child("profileUrl").value.toString()
                        val name = i.child("firstName").value.toString()
                        val userID = i.key.toString()
                        val lastMsg = ""


                        userArrayList.add(UserChatsView(name, imgUrl, lastMsg, userID))
                    }  }


                var adapter = UserChatAdapter(context!!, userArrayList)
                chatsView.adapter = adapter
                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })








        return view
    }
}