package com.example.newsapp.services

import com.example.newsapp.models.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//All functions are defined here...
interface DestinationService {
    @GET("top-headlines")
    fun getArticles(@Query("country") country:String,@Query("apiKey") apiKey:String):Call<News>
}