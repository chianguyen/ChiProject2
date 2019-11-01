package org.mp.chiproject2.repositories

import android.content.Context
import android.text.method.MultiTapKeyListener
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.tools.MyApplication
import retrofit2.Call
import retrofit2.Response

class AuthRepository {


    fun userLogin(email: String, password: String): MutableLiveData<JsonObject>{

        val loginResponse = MutableLiveData<JsonObject>()

        var apiInterface: ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var userCall = apiInterface.userLogin(email, password)

        userCall.enqueue(object : retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.i("LOGIN ERROR", t.message.toString())
                loginResponse.value?.getAsJsonObject(t.message)
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                loginResponse.value = response.body()
                Log.i("USER OBJECT", loginResponse.toString())
                Log.i("USER MSG", loginResponse.value?.get("msg")?.toString())

                var userID = loginResponse.value?.get("userid").toString()
                var userType = loginResponse.value?.get("usertype").toString()

                var sp = MyApplication.context.getSharedPreferences("test_chamber", Context.MODE_PRIVATE)
                var editor = sp.edit()

                editor.putString("uID", userID)
                editor.putString("uType", userType)

                editor.commit()
            }
        })

        return loginResponse

    }

    fun userRegister(email: String, lemail: String, password: String, accType: String): MutableLiveData<String>{

        val registerResponse = MutableLiveData<String>()

        var apiInterface: ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)
        var userCall = apiInterface.userReg(email, lemail, password, accType)

        userCall.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("REG ERROR", t.message.toString())
                registerResponse.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                registerResponse.value = response.body()?.string()
                if(response.isSuccessful){
                    registerResponse.value = response.body()?.string()
                }
                else{
                    registerResponse.value = response.errorBody()?.string()
                }
            }
        })

        return registerResponse
    }



}