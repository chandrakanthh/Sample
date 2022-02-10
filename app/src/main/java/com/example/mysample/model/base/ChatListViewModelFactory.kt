package com.example.mysample.model.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysample.model.repos.ChatListRepository
import com.example.mysample.viewModel.ChatListViewModel
import java.lang.IllegalArgumentException

class ChatListViewModelFactory(private val chatListRepository: ChatListRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ChatListViewModel::class.java)){
            ChatListViewModel(this.chatListRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel not fount for this factory")
        }
    }
}