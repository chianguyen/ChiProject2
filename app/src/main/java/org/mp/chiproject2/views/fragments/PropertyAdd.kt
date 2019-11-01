package org.mp.chiproject2.views.fragments


import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.fragment_property_add.*
import kotlinx.android.synthetic.main.fragment_property_add.view.*
import kotlinx.android.synthetic.main.fragment_property_list.view.*
import okhttp3.ResponseBody

import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class PropertyAdd : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_add, container, false)

        var sp: SharedPreferences = view.context!!.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        var userId = sp.getString("user_id", "DEFAULT")
        var userType = sp.getString("user_type", "DEFAULT")

        view.btn_prop_add.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var addressA = property_add_address.text.toString()
                var cityA    = property_add_city.text.toString()
                var stateA   = property_add_state.text.toString()
                var countryA = property_add_country.text.toString()
                var prostatA = property_add_prostatus.text.toString()
                var mortageA = property_add_mortageinfo.text.toString()
                var priceA   = property_add_price.text.toString()

                if (userType != null && userId != null) {

                    var latitude = ""
                    var longitude = ""
                    var geocoderMatches: List<Address>? = null

                    try {
                        var fullAddress = "$addressA, $cityA, $stateA, $countryA"
                        geocoderMatches = Geocoder(view.context).getFromLocationName(fullAddress, 1)
                    }
                    catch (e: Exception){
                        Log.e("ERROR", e.toString())
                    }

                    if (geocoderMatches != null){
                        latitude = geocoderMatches[0].latitude.toString()
                        longitude = geocoderMatches[0].longitude.toString()

                        addProperty(addressA, cityA, stateA, countryA, prostatA, priceA, mortageA, userId, userType, latitude, longitude)

                        Log.i("LONGLAT", "lat = $latitude, long = $longitude")



                    }

                }

            }
        })

        return view
    }

    fun addProperty(address: String, city: String, state: String,
                    country: String, proStat: String, price: String,
                    mortage: String, userid: String, usertype: String,
                    latitude: String, longitude: String)
    {

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var propCall = apiInterface.addProperty(address, city, state, country, proStat, price, mortage, userid, usertype, latitude, longitude)

        propCall.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("PROP ADD ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("PROP ADD RES", response.body()!!.string())
                Toast.makeText(view?.context, "Property added", Toast.LENGTH_SHORT).show()
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, PropertyListL()).addToBackStack(null).commit()
            }
        })


    }


}
