package com.example.mysample.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysample.model.ChatListsModel
import com.example.mysample.model.repos.ChatListRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatListViewModel(private val chatListRepository: ChatListRepository): ViewModel() {
    val chatLists = MutableLiveData<List<ChatListsModel>>()
    val errorMsg = MutableLiveData<String>()

    fun getChatListData(){
        val response = chatListRepository.getAllChatData()
        response.enqueue(object : Callback<List<ChatListsModel>>{
            override fun onResponse(call: Call<List<ChatListsModel>>, response: Response<List<ChatListsModel>>) {
                if(response.isSuccessful) chatLists.postValue(response.body())
                else errorMsg.postValue("Something went wrong. please try again later")
            }

            override fun onFailure(call: Call<List<ChatListsModel>>, t: Throwable) {
                errorMsg.postValue(t.message)
            }
        })
    }
}