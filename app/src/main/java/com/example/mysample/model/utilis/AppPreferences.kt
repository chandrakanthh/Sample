package com.example.mysample.model.utilis

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    const val PIC_URL = "https://picsum.photos/200/300"
    private const val NAME = "Sample"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var sharedPreferences: SharedPreferences

    private val IS_LOGGEDIN = Pair("is_loggedIn",false)
    private val USER_EMAIL = Pair("user_email","")

    fun init(context: Context){
        sharedPreferences = context.getSharedPreferences(NAME,MODE)
    }

    inline fun SharedPreferences.edit(operation : (SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    fun clearAll(){
        sharedPreferences.edit().clear().apply()
    }

    var isUserLoggedIn: Boolean
    get() = sharedPreferences.getBoolean(IS_LOGGEDIN.first, IS_LOGGEDIN.second)
    set(value) = sharedPreferences.edit{it.putBoolean(IS_LOGGEDIN.first,value)}

    var userEmail: String?
    get() = sharedPreferences.getString(USER_EMAIL.first, USER_EMAIL.second)
    set(value) = sharedPreferences.edit{it.putString(USER_EMAIL.first,value)}
}