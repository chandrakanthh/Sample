package com.example.mysample.model.repos

import android.app.Application
import android.util.Log
import com.example.mysample.model.LoginModel
import com.example.mysample.model.utilis.AppPreferences

class LoginRepository(private val application: Application) {
    //val loginResponse = MutableLiveData<Boolean>()
    fun saveLoginData(get: LoginModel) {
        AppPreferences.init(application)
        AppPreferences.isUserLoggedIn = true
        AppPreferences.userEmail = get.Email
        Log.e("chandra 0","saveLoginData")

    }

}



