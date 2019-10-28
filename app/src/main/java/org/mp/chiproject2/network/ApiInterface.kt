package org.mp.chiproject2.network

import android.telecom.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("pro_mgt_reg.php?&{email}&landlord_email={llemail}&password={pwd}&account_for={type}")
    fun userReg(): Call

    @GET("pro_mgt_login.php?email={email}&password={pwd}")
    fun userLogin(): Call

}