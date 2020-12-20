package com.example.newsapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    //Server URL
    private const val BASE_URL="https://newsapi.org/v2/"
    //Interceptor for logging our requests and responses
    private val logger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    //create OkHttpClient.Builder()
    private val okHttp=OkHttpClient.Builder()
                                        .connectTimeout(100, TimeUnit.SECONDS)
                                        .readTimeout(100,TimeUnit.SECONDS)
                                        .addInterceptor(logger)
    private val retrofitBuilder=Retrofit.Builder().baseUrl(BASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .client(okHttp.build())
    private val retrofit= retrofitBuilder.build()

    // This function is used to call our function using retrofit.....our interface class is passed here
    fun <T> buildService(service: Class<T>):T{
        return retrofit.create(service)
    }
}