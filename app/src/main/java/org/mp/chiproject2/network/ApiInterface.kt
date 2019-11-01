package org.mp.chiproject2.network

import com.google.gson.JsonObject
import okhttp3.Response
import okhttp3.ResponseBody
import org.mp.chiproject2.models.PropertyLList
import org.mp.chiproject2.models.PropertyTList
import org.mp.chiproject2.models.TenantList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("pro_mgt_reg.php")
    fun userReg(@Query("email") email: String,
                @Query("landlord_email") lemail: String,
                @Query("password") password: String,
                @Query("account_for") acctype: String): retrofit2.Call<ResponseBody> //ResponseBody


    @GET("pro_mgt_login.php")
    fun userLogin(@Query("email") email: String,
                  @Query("password") password: String): retrofit2.Call<JsonObject>


    @GET("pro_mgt_forgot_pass.php")
    fun retrievePwd(@Query("email") email: String): retrofit2.Call<JsonObject>


    @GET("property.php")
    fun showPropertyLList(@Query("userid") userId: String,
                         @Query("usertype") userType: String): retrofit2.Call<PropertyLList>


    @GET("pro_mgt_property_all.php")
    fun showPropertyTList(): retrofit2.Call<PropertyTList>

    @GET("pro_mgt_property_all.php")
    fun propertyForMap(@Query("userid") userId: String,
                       @Query("usertype") userType: String): retrofit2.Call<PropertyLList>


    @GET("pro_mgt_tenent_details.php")
    fun showTennantList(@Query("landlordid") landlordid: String): retrofit2.Call<TenantList>


    @GET("pro_mgt_add_pro.php")
    fun addProperty(@Query("address") address: String,
                    @Query("city") city: String,
                    @Query("state") state: String,
                    @Query("country") country: String,
                    @Query("pro_status") pro_status: String,
                    @Query("purchase_price") purchase_price: String,
                    @Query("mortage_info") mortage_info: String,
                    @Query("userid") userid: String,
                    @Query("usertype") usertype: String,
                    @Query("latitude") latitude: String,
                    @Query("longitude") longitude: String): retrofit2.Call<ResponseBody>


    @GET("remove-property.php")
    fun rmvProperty(@Query("propertyid") propertyID: String): retrofit2.Call<ResponseBody>


    @GET("pro_mgt_add_tenants.php")
    fun addTenant(@Query("name") name: String,
                  @Query("email") email: String,
                  @Query("address") address: String,
                  @Query("mobile") mobile: String,
                  @Query("propertyid")  propertyid: String,
                  @Query("landlordid") landlordid: String): retrofit2.Call<ResponseBody>





}