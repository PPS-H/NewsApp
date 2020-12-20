package com.example.newsapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.services.ApiKey
import com.example.newsapp.services.NoInternetConnection
import com.example.newsapp.R
import com.example.newsapp.adapter.MyAdapter
import com.example.newsapp.models.News
import com.example.newsapp.services.DestinationService
import com.example.newsapp.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var PERMISSION_CODE=1
    private var sharedPrefrence: SharedPreferences? =null
    private var editor: SharedPreferences.Editor? =null
    private var shrd: SharedPreferences? =null
    private var editor2: SharedPreferences.Editor? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shrd=getSharedPreferences("permission", MODE_PRIVATE)
        editor2=shrd?.edit()
        //Permission
        if(shrd?.getString("applied","false")=="false") {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                editor2?.putString("applied", "true")
                editor2?.apply()
                Toast.makeText(
                    this,
                    "This app uses your device internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                requestPermission()
            }
        }
        val layoutManager=LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        newsView.layoutManager=layoutManager
        loadNews()

    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
    private fun requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.INTERNET)){
            }else{
            editor2?.putString("applied","true")
            editor2?.apply()
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET),
            PERMISSION_CODE)
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==PERMISSION_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults.size>0){
                Toast.makeText(this,"Permission Granted Successfully!!",Toast.LENGTH_SHORT).show()

            }else{
                editor2?.putString("applied","true")
                editor2?.apply()
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET),
                    PERMISSION_CODE)
            }
        }
    }
    //Top-menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return true
    }
    //Menu item select->Dark and Light Theme
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        sharedPrefrence=getSharedPreferences("Theme", MODE_PRIVATE)
        editor= sharedPrefrence?.edit()
        if(item.title=="Dark Theme") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor?.putString("MODE", "true")
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor?.putString("MODE", "false")
        }
        editor?.apply()
        finish()
        return true
    }
    //Fetching data from the News API
    private fun loadNews() {
        var destination: DestinationService =ServiceBuilder.buildService(DestinationService::class.java)
        var requestCall: Call<News> =destination.getArticles("in", ApiKey.API_KEY)
        //Call interface contains all the information about http requests and responses
        requestCall.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    var l: News = response.body()!!
                    var list:List<News> =l.articles
                    val adapter = MyAdapter(this@MainActivity, list)
                    newsView.adapter = adapter
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Failed to load news!",Toast.LENGTH_SHORT).show()
                var intent= Intent(this@MainActivity, NoInternetConnection::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}

