package com.example.mysample.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysample.R
import com.example.mysample.databinding.ActivityLoginBinding
import com.example.mysample.model.repos.LoginRepository
import com.example.mysample.model.base.LoginViewModelFactory
import com.example.mysample.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        loginViewModel = ViewModelProvider(this,
            LoginViewModelFactory(application,LoginRepository(application))
        ).get(LoginViewModel::class.java)
        loginBinding.lifecycleOwner=this
        loginBinding.apply {
            viewModel = loginViewModel
        }
        //loginBinding.loginBtn.setOnClickListener(this)

        loginViewModel.loginResponse.observe(this, Observer {
            if(it.equals(true)) navToChatPage()
        })

        for(i in 0..40) {
            loginBinding.tabsamples.addTab(loginBinding.tabsamples.newTab().setText("Cat $i"))
        }
        loginBinding.appLogo.setOnClickListener {
            loginBinding.tabsamples.getTabAt(10)!!.select()
        }
    }

    /*override fun onClick(v: View?) {
        when(v?.id){
            R.id.login_btn ->{
                if(validateData()) {
                    navToChatPage()
                    saveData()
                }
            }
        }
    }

    private fun saveData() {
        AppPreferences.init(getApplication())
        AppPreferences.isUserLoggedIn = true
        AppPreferences.userEmail = loginBinding.emailAddressTEt.text.toString()
    }*/

    private fun navToChatPage() {
        startActivity(Intent(this, ChatListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    /*private fun validateData(): Boolean {
        if(loginBinding.emailAddressTEt.text.toString().isNullOrEmpty()){
            loginBinding.emailAddressTEtLayer.error = "Please enter email"
            return false
        } else loginBinding.emailAddressTEtLayer.error = ""

        if(loginBinding.passwordTEt.text.toString().isNullOrEmpty()){
            loginBinding.passwordTEtLayer.error = "Please enter password"
            return false
        } else loginBinding.passwordTEtLayer.error = ""

        if(loginBinding.passwordTEt.text.toString().length <10){
            loginBinding.passwordTEtLayer.error = "Password must contains 10 characters"
            return false
        } else loginBinding.passwordTEtLayer.error = ""

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(loginBinding.emailAddressTEt.text.toString()).matches()){
            loginBinding.emailAddressTEtLayer.error = "Please enter valid email"
            return false
        } else loginBinding.emailAddressTEtLayer.error = ""

        return true

    }*/
}