package com.example.mysample.model.services

import com.example.mysample.model.ChatListsModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getChatListData(): Call<List<ChatListsModel>>
}