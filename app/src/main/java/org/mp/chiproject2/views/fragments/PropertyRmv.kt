package org.mp.chiproject2.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_property_rmv.*
import kotlinx.android.synthetic.main.fragment_property_rmv.view.*
import okhttp3.ResponseBody

import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PropertyRmv : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_rmv, container, false)

        view.btn_prop_rmv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var propertyID = property_rmv_id.text.toString()

                rmvProperty(propertyID)
            }
        })

        return view
    }

    fun rmvProperty(propertyID: String){

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var userCall = apiInterface.rmvProperty(propertyID)

        userCall.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("PROP RMV EROR", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("PROP RMV RES", response.body()!!.string())
                Toast.makeText(view?.context, response.body()!!.string(), Toast.LENGTH_SHORT).show()
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, PropertyListL()).addToBackStack(null).commit()
            }
        })


    }

}
