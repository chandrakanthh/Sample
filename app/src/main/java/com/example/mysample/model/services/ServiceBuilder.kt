package com.example.mysample.model.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val baseURL = "https://jsonplaceholder.typicode.com/"

    private val okHttpClient = OkHttpClient.Builder()
    private val builder =Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())
    private val retrofit = builder.build()

    fun<T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }
}