package org.mp.chiproject2.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_property_list_t.*
import kotlinx.android.synthetic.main.fragment_property_list_t.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyL
import org.mp.chiproject2.models.PropertyTList
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.views.adapters.PropertyTAdapter
import retrofit2.Call
import retrofit2.Response

class PropertyListT : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_list_t, container, false)

        view.recyclerViewPropT.layoutManager = LinearLayoutManager(view.context)
        showPropertyTList()

        return view
    }

    fun showPropertyTList() {

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var propCall = apiInterface.showPropertyTList()

        propCall.enqueue(object: retrofit2.Callback<PropertyTList>{

            override fun onFailure(call: Call<PropertyTList>, t: Throwable) {
                Log.e("PROP T ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<PropertyTList>, response: Response<PropertyTList>) {

                var propertyTitems = response.body()

                for(i in 0 until propertyTitems!!.propertiesT.size) {

                    if(!propertyTitems.propertiesT[i].propertylongitude.isNullOrEmpty() && !propertyTitems.propertiesT[i].propertylatitude.isNullOrEmpty()) {
                        view?.recyclerViewPropT?.adapter =
                            PropertyTAdapter(propertyTitems.propertiesT, view!!.context)
                    }

                }

            }
        })
    }

}
