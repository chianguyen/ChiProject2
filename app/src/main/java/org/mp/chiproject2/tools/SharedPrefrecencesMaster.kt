package org.mp.chiproject2.tools

import android.content.Context

class SharedPrefrecencesMaster {

    private val sp = MyApplication.context.getSharedPreferences("Chamber", Context.MODE_PRIVATE)
    private val editor = sp.edit()

    fun loginUser(email: String, password: String, userType: String, apiKey: String){
        editor.putString("user_email", email)
        editor.putString("user_pwd", password)
        editor.putString("user_type", userType)
        editor.putString("api_key", apiKey)
        editor.commit()
    }

}