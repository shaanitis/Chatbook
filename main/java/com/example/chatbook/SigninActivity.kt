package com.example.chatbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class SigninActivity : AppCompatActivity() {


    val auth= FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()
        setContentView(R.layout.activity_signin)
        val signInBtn: Button = findViewById(R.id.signInButton)
        val wannaSignUp: Button = findViewById(R.id.wannaSignUp)
        val tvEmail = findViewById<EditText>(R.id.tEmail)
        val tvPass = findViewById<EditText>(R.id.tPass)
        val progressBar: ProgressBar = findViewById(R.id.progressBar2)

            progressBar.visibility = View.INVISIBLE


        signInBtn.setOnClickListener() {

            if (tvEmail.text.toString().isEmpty()) {
                tvEmail.setError("Enter Email")
                tvEmail.requestFocus()
            } else if (tvPass.getText().toString().isEmpty()) {
                tvPass.setError("Enter tvPass")
                tvPass.requestFocus()
            } else if (Patterns.EMAIL_ADDRESS.matcher(tvEmail.toString()).matches()) {
                tvEmail.setError("Enter Valid Email Address")
                tvEmail.requestFocus()

            } else {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(tvEmail.text.toString(), tvPass.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("", "signInWithEmail:success")
                                val user = auth.currentUser
                                val intent = Intent(this, UserActivity::class.java)
                                startActivity(intent)

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("", "signInWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                                if (progressBar.isActivated) {
                                    progressBar.visibility = View.INVISIBLE
                                }

                            }
                        }


            }}
            wannaSignUp.setOnClickListener() {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }




    }}
