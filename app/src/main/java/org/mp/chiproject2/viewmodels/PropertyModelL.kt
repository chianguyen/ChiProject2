package org.mp.chiproject2.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mp.chiproject2.models.PropertyLList
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

class PropertyModelL: ViewModel() {

    lateinit var propertyLModel : MutableLiveData<PropertyLList>

    fun getPropertyLList() : MutableLiveData<PropertyLList>{
        if (propertyLModel == null){
            var propertyLModel:MutableLiveData<PropertyLList>
   //         showPropertyLList()
        }

        return propertyLModel
    }

/*    fun showPropertyLList(userID: String, userType: String){

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var propCall = apiInterface.showPropertyLList(userID, userType)

        propCall.enqueue(object: retrofit2.Callback<PropertyLList>{
            override fun onFailure(call: Call<PropertyLList>, t: Throwable) {
                Log.e("PROP L ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<PropertyLList>, response: Response<PropertyLList>) {
                Log.i("PROP L RES", response.message().toString())
            }
        })

    }*/

}