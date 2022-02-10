package com.example.mysample.view.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mysample.R
import com.example.mysample.databinding.ActivityMainBinding
import com.example.mysample.model.utilis.AppPreferences

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityBinding: ActivityMainBinding
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityBinding.loginBtn.setOnClickListener(this)
        AppPreferences.init(context)
        if(AppPreferences.isUserLoggedIn){
            navToHome()
        }
    }

    private fun navToHome() {
        startActivity(Intent(this, ChatListActivity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.login_btn -> onLoginClick()
        }
    }

    fun onLoginClick(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
}