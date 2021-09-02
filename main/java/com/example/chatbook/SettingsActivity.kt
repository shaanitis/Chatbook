package com.example.chatbook

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.chatbook.databinding.ActivitySettingsBinding
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class SettingsActivity : AppCompatActivity() {



    var imageUri: Uri?= null
    val auth= FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference
    val NameList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        var galleryImage= findViewById<ImageView>(R.id.profileImg)
        val ref= FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val profile: String= snapshot.child("profileUrl").getValue().toString()
                Picasso.get().load(profile).placeholder(R.drawable.user).into(galleryImage)
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        var storageReference: StorageReference?= null
        var imageName= UUID.randomUUID().toString() + ".jpg"
         val chooseBtn: ImageButton= findViewById(R.id.choose)
        val save: Button= findViewById(R.id.save)


        @Suppress("DEPRECATION")
        var progressDialogue: ProgressDialog?= null







        save.setOnClickListener(){
if(imageUri==null){
    Toast.makeText(this, "Select image first", Toast.LENGTH_SHORT).show()
}else {


    progressDialogue = ProgressDialog(this)
    progressDialogue!!.setTitle("Uploading Snap...")
    progressDialogue!!.show()


    storageReference =
            FirebaseStorage.getInstance().getReference("ProfilePics/" + imageName)
    storageReference!!.putFile(imageUri!!).addOnSuccessListener {

        galleryImage?.setImageURI(imageUri)


        storageReference?.downloadUrl?.addOnSuccessListener(OnSuccessListener<Any?> { downloadUrl ->

            val url = downloadUrl.toString()
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(auth.currentUser?.uid.toString()).child("profileUrl")
                    .setValue(url)

            if (progressDialogue!!.isShowing) {
                progressDialogue!!.hide()
            }

            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)

            Toast.makeText(this, "Profile picture updated", Toast.LENGTH_SHORT).show()


        })?.addOnFailureListener(OnFailureListener {
            Toast.makeText(this, "Failed to get Url", Toast.LENGTH_SHORT).show()
            if (progressDialogue!!.isShowing) {
                progressDialogue!!.hide()
            }
        })


    }.addOnFailureListener() {
        Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
        if (progressDialogue!!.isShowing) {
            progressDialogue!!.hide()
        }

    }
}
           }

        chooseBtn.setOnClickListener(){

            ImagePicker.Companion.with(this)
                .galleryOnly()
                .start(101)
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==101 && resultCode== Activity.RESULT_OK && data!=null){
            imageUri= data.data
            var galleryImage= findViewById<ImageView>(R.id.profileImg)
            if(imageUri!= null) {

                Picasso.get().load(imageUri).into(galleryImage)
                /* galleryImage.setImageURI(imageUri)*/


            } }

        super.onActivityResult(requestCode, resultCode, data)
    }


}