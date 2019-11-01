package org.mp.chiproject2.views.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_proplist_add_medium.view.*
import kotlinx.android.synthetic.main.fragment_tennant_add.*
import kotlinx.android.synthetic.main.fragment_tennant_add.view.*
import okhttp3.ResponseBody

import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class TennantAdd : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_tennant_add, container, false)

        var sp: SharedPreferences = view.context!!.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        var landlordId = sp.getString("user_id", "DEFAULT")

        view.btn_tenant_add.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var newTenantName    = tenant_add_name.text.toString()
                var newTenantEmail   = tenant_add_email.text.toString()
                var newTenantAddress = tenant_add_address.text.toString()
                var newTenantMobile  = tenant_add_mobile.text.toString()
                var newTenantPropID  = tenant_add_propID.text.toString()

                addTenant(newTenantName, newTenantEmail, newTenantAddress, newTenantMobile, newTenantPropID, landlordId.toString())

            }
        })


        return view
    }

    fun addTenant(name: String, email: String, address: String, mobile: String, propertyid: String, landlordid: String){

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var userCall = apiInterface.addTenant(name, email, address, mobile, propertyid, landlordid)

        userCall.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("TEN ADD ERROR", t.message.toString())
                Toast.makeText(view?.context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("TEN ADD RES", response.message().toString())
                Log.i("TEN ADD RESS", "Added Tennant to property $propertyid at landlord $landlordid")
                Toast.makeText(view?.context, "Tenant $name added to property $propertyid", Toast.LENGTH_SHORT).show()
            }
        })


    }

}
