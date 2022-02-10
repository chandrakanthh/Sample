package com.example.mysample.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mysample.R
import com.example.mysample.model.LoginModel
import com.example.mysample.model.repos.LoginRepository

class LoginViewModel(application: Application,private val repository: LoginRepository)
    : AndroidViewModel(application) {


    val loginRequest = ObservableField<LoginModel>()
    val passwordError = ObservableField<String>()
    val emailError = ObservableField<String>()
    val loginResponse = MutableLiveData<Boolean>()


    init {
        loginRequest.set(LoginModel())
    }

    fun onLoginClicks(){
        Log.e("chandra 0","onLoginClicks")
        if(validateData()){
            repository.saveLoginData(loginRequest.get()!!)
            loginResponse.postValue(true)
        }
    }


    private fun validateData(): Boolean {
        val loginRequest = loginRequest.get()
        if(loginRequest?.Email.isNullOrEmpty()){
            //emailError.set("Please enter email")
            emailError.set(R.string.app_name.toString())
            return false
        } else emailError.set("")

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(loginRequest?.Email.toString()).matches()){
            emailError.set("Please enter valid email")
            return false
        } else emailError.set("")


        if(loginRequest?.Password.isNullOrEmpty()){
            passwordError.set("Please enter password")
            return false
        } else passwordError.set("")

        if(loginRequest?.Password?.length!! <10){
            passwordError.set("Password must contains 10 characters")
            return false
        } else passwordError.set("")

        return true

    }


}