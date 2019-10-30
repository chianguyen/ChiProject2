package org.mp.chiproject2.models

import com.google.gson.annotations.SerializedName

data class TenantList(
    @SerializedName("Tenants")
    var tennants: List<Tenant>
)