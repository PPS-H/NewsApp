package com.example.newsapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.newsapp.R
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        var intent=getIntent()
        var URL: String =intent.getStringExtra("url")
        webView.webViewClient= WebViewClient()
        webView.apply {
            loadUrl(URL)
            settings.javaScriptEnabled=true
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}