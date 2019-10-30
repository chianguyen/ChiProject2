package org.mp.chiproject2.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.tools.MyApplication
import org.mp.chiproject2.views.fragments.PropertyListL

class PropertyLRepo {

    var propertyLMutableLiveData = MutableLiveData<PropertyListL>()

    fun showProperty(): MutableLiveData<PropertyListL>{

        var sp: SharedPreferences = MyApplication.context.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var userL = sp.getString("user_type", "Default")
        var userID = sp.getString("user_id", "Default")

        if (userID != null && userL != null) {

        }

        return propertyLMutableLiveData

    }

}