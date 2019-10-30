package org.mp.chiproject2.models

import com.google.gson.annotations.SerializedName

data class PropertyTList(
    @SerializedName("Property")
    var propertiesT: List<PropertyT>)