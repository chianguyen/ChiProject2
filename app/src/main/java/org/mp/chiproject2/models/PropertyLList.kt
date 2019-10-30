package org.mp.chiproject2.models

import com.google.gson.annotations.SerializedName

data class PropertyLList(
    @SerializedName("Property")
    var propertiesL: List<PropertyL>)