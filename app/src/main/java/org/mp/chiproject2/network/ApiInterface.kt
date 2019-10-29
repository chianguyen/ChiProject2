package org.mp.chiproject2.network

import android.telecom.Call
import com.firebase.ui.auth.data.model.User
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("pro_mgt_reg.php?")
    fun userReg(@Query("email") email: String,
                @Query("landlord_email") lemail: String,
                @Query("password") password: String,
                @Query("account_for") acctype: String): retrofit2.Call<ResponseBody> //ResponseBody

    @GET("pro_mgt_login.php?")
    fun userLogin(@Query("email") email: String,
                  @Query("password") password: String): retrofit2.Call<JsonObject>

    @GET("pro_mgt_forgot_pass.php?")
    fun retrievePwd(@Query("email") email: String): retrofit2.Call<JsonObject>
}