package com.example.newsapp.models

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        var sharedPrefrence=getSharedPreferences("Theme", MODE_PRIVATE)
        var mode=sharedPrefrence.getString("MODE","false")
        if(mode=="true")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}