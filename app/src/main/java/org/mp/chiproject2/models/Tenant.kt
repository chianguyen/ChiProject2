package org.mp.chiproject2.models
import com.google.gson.annotations.SerializedName

data class Tenant(
    @SerializedName("id")
    var id: String,
    @SerializedName("landlordid")
    var landlordid: String,
    @SerializedName("propertyid")
    var propertyid: String,
    @SerializedName("tenantaddress")
    var tenantaddress: String,
    @SerializedName("tenantemail")
    var tenantemail: String,
    @SerializedName("tenantmobile")
    var tenantmobile: String,
    @SerializedName("tenantname")
    var tenantname: String
)