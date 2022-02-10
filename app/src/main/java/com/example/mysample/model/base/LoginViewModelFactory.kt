package com.example.mysample.model.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysample.model.repos.LoginRepository
import com.example.mysample.viewModel.LoginViewModel
import java.lang.IllegalArgumentException

class LoginViewModelFactory(private val application: Application,private val loginRepository: LoginRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            LoginViewModel(application,this.loginRepository) as T
        } else {
            throw IllegalArgumentException ("View Model was not found")
        }
    }
}