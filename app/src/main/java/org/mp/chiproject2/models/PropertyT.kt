package org.mp.chiproject2.models

import com.google.gson.annotations.SerializedName

class PropertyT(
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
    var propertystatus: String,

    @SerializedName("propertyuserid")
    var propertyuserid: String,

    @SerializedName("propertyusertype")
    var propertyusertype : String,

    @SerializedName("propertylatitude")
    var propertylatitude : String,

    @SerializedName("propertylongitude")
    var propertylongitude: String

)