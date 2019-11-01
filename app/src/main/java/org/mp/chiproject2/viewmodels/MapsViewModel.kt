package org.mp.chiproject2.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mp.chiproject2.models.MapAddress
import org.mp.chiproject2.models.PropertyL
import org.mp.chiproject2.models.PropertyLList
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.tools.MyApplication
import retrofit2.Call
import retrofit2.Response

class MapsViewModel: ViewModel() {

    fun showPropertyLList(): LiveData<List<PropertyL>> {

        var mapThing = MutableLiveData<List<PropertyL>>()

        var sp: SharedPreferences = MyApplication.context.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        var userId = sp.getString("user_id", null).toString()
        var userType = sp.getString("user_type", null).toString()

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var propCall = apiInterface.showPropertyLList(userId, userType)

        var lats = ArrayList<String>()
        var longs = ArrayList<String>()
        var address = ArrayList<String>()

        propCall.enqueue(object: retrofit2.Callback<PropertyLList>{
            override fun onFailure(call: Call<PropertyLList>, t: Throwable) {
                Log.e("PROP L ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<PropertyLList>, response: Response<PropertyLList>) {
                Log.i("PROP L RES", response.body().toString())

                var propertyLitems = response.body()

                Log.i("PROP SIZE", propertyLitems!!.propertiesL.size.toString())


                mapThing.value = propertyLitems!!.propertiesL


/*                for (i in 0 until propertyLitems.propertiesL.size) {

                    if (!propertyLitems.propertiesL[i].propertylongitude.isEmpty() && !propertyLitems.propertiesL[i].propertylatitude.isEmpty()) {

                        var fullAddress = "${propertyLitems.propertiesL[i].propertyaddress}\n" +
                                "${propertyLitems.propertiesL[i].propertycity}, ${propertyLitems.propertiesL[i].propertystate}, ${propertyLitems.propertiesL[i].propertycountry}"

                        longs.add(propertyLitems.propertiesL[i].propertylongitude)
                        lats.add(propertyLitems.propertiesL[i].propertylatitude)
                        address.add(fullAddress)

                        Log.i("LONG $i", propertyLitems.propertiesL[i].propertylongitude)
                        Log.i("LAT $i", propertyLitems.propertiesL[i].propertylatitude)
                    }
                }*/

//                mapThing.value = Triple(longs, lats, address)

            }
        })

        return mapThing
    }
}