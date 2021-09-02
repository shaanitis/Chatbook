package com.example.chatbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.chatbook.Adapters.FragmentsAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class UserActivity : AppCompatActivity() {
    val auth= FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        this.supportActionBar?.hide()
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar)
       val viewPager: ViewPager= findViewById(R.id.viewPager)
        val tabLayout: TabLayout= findViewById(R.id.tabLayout)



        viewPager.setAdapter(FragmentsAdapter(supportFragmentManager))
         tabLayout.setupWithViewPager(viewPager)


       val intentUrl= intent.getStringExtra("url")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflator: MenuInflater= menuInflater
        inflator.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.logout){
            auth.signOut()

            val intent= Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }
        if(item.itemId== R.id.settings){


            val intent= Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}



