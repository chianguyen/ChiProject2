package org.mp.chiproject2.models
import com.google.gson.annotations.SerializedName

data class PropertyL(

    @SerializedName("id")
    var id: String,

    @SerializedName("propertyaddress")
    var propertyaddress: String,

    @SerializedName("propertycity")
    var propertycity: String,

    @SerializedName("propertycountry")
    var propertycountry: String,

    @SerializedName("propertymortageinfo")
    var propertymortageinfo: String,

    @SerializedName("propertypurchaseprice")
    var propertypurchaseprice: String,

    @SerializedName("propertystate")
    var propertystate: String,

    @SerializedName("propertystatus")
    var propertystatus: String
)

