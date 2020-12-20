package com.example.newsapp.services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapp.R
import com.example.newsapp.activities.MainActivity
import kotlinx.android.synthetic.main.activity_no_internet_connection.*

class NoInternetConnection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet_connection)
        retry_btn.setOnClickListener{
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}