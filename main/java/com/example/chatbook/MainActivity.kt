package com.example.chatbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.chatbook.Models.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportActionBar?.hide()
        val signUpBtn: Button= findViewById(R.id.signInButton)
        val wannaSignIn: Button= findViewById(R.id.wannaSignUp)
        val tEmail = findViewById<EditText>(R.id.tEmail)
        val tName= findViewById<EditText>(R.id.tName)
        val tPass = findViewById<EditText>(R.id.tPass)
        val progress: ProgressBar= findViewById(R.id.progressBar)
            progress.visibility = View.INVISIBLE

        auth= Firebase.auth

       ///////check if logged in///////////
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent= Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

                      //////////////

        signUpBtn.setOnClickListener(){
             progress.visibility = View.VISIBLE

            if (tEmail.text.toString().isEmpty()) {
                tEmail.setError("Enter Email Address")
                tEmail.requestFocus()

            } else if (tPass.text.toString().isEmpty()) {
                tPass.setError("Enter tPass")
                tPass.requestFocus()
            }
            else if (tName.getText().toString().isEmpty()) {
                tName.setError("Enter Username")
                tName.requestFocus()

            } else if (Patterns.EMAIL_ADDRESS.matcher(tEmail.toString()).matches()) {
                tEmail.setError("Enter Valid Email Address")
                tEmail.requestFocus()

            } else {

                auth.createUserWithEmailAndPassword (tEmail
                        .text.toString(), tPass.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                    database = FirebaseDatabase.getInstance().getReference("Users")
                                    val User= UserInfo(email = tEmail.text.toString(), firstName = tName.text.toString(), pass = tPass.text.toString())
                                    database.child(auth.currentUser?.uid.toString()).setValue(User)




                                val user = auth.currentUser
                                intent = Intent(this, UserActivity::class.java)

                                startActivity(intent)


                            }else{
                                // If sign in fails, display a message to the user.
                              /*  Log.w("", "createUserWithEmail:failure", task.exception)*/
                                Toast.makeText(baseContext, "Signup Failed",
                                        Toast.LENGTH_SHORT).show()
                                 if(progress.isActivated){
                                     progress.visibility= View.INVISIBLE
                                 }
                            }                            }
                        }

        }


      wannaSignIn.setOnClickListener(){
        val intent= Intent(this, SigninActivity::class.java)
        startActivity(intent)
        this.supportActionBar?.hide()}

       
    }
}