package com.example.mysample.model.repos

import com.example.mysample.model.services.ApiService

class ChatListRepository(private val apiService: ApiService) {
    fun getAllChatData() = apiService.getChatListData()
}