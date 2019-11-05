package org.mp.chiproject2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mp.chiproject2.models.PropertyT
import org.mp.chiproject2.models.PropertyTList
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

class MapsTViewModel: ViewModel() {

    //fun showPropertyTList(): LiveData<Triple<ArrayList<String>, ArrayList<String>, ArrayList<String>>>{

    fun showPropertyTList(): LiveData<List<PropertyT>>{
    //    var mapThingT = MutableLiveData<Triple<ArrayList<String>, ArrayList<String>, ArrayList<String>>>()
        var mapThingT = MutableLiveData<List<PropertyT>>()

        var latsT = ArrayList<String>()
        var longsT = ArrayList<String>()
        var addressT = ArrayList<String>()

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)
        var propCallT = apiInterface.showPropertyTList()

        propCallT.enqueue(object: retrofit2.Callback<PropertyTList>{
            override fun onFailure(call: Call<PropertyTList>, t: Throwable) {
                Log.e("PROP T ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<PropertyTList>, response: Response<PropertyTList>) {
                Log.i("PROP T RES", response.body().toString())

                var propertyTitems = response.body()

                Log.i("PROP T SIZE", propertyTitems!!.propertiesT.size.toString())

                mapThingT.value = propertyTitems.propertiesT

/*                for(i in 0 until propertyTitems!!.propertiesT.size){
                    if(!propertyTitems!!.propertiesT[i].propertylatitude.isNullOrEmpty() && !propertyTitems!!.propertiesT[i].propertylatitude.isNullOrEmpty()){

                        var fullAddressT = "${propertyTitems!!.propertiesT[i].propertyaddress}\n" +
                                "${propertyTitems!!.propertiesT[i].propertycity}, ${propertyTitems!!.propertiesT[i].propertystate}, ${propertyTitems!!.propertiesT[i].propertycountry}"

                        longsT.add(propertyTitems!!.propertiesT[i].propertylongitude)
                        latsT.add(propertyTitems!!.propertiesT[i].propertylatitude)
                        addressT.add(fullAddressT)

                    }
                }*/

 //               mapThingT.value = Triple(longsT, latsT, addressT)

            }
        })

        return mapThingT
    }


}