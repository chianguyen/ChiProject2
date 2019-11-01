package org.mp.chiproject2.views.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tennant_list.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.views.adapters.TennantAdapter
import org.mp.chiproject2.models.Tenant
import org.mp.chiproject2.models.TenantList
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class TennantListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_tennant_list, container, false)

        view.recyclerViewTennant.layoutManager = LinearLayoutManager(context)

        var sp: SharedPreferences = view.context!!.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        var landlordId = sp.getString("user_id", "DEFAULT")

        if(landlordId != null) {
            Log.i("PROP INFO", "Calling landlord ID: $landlordId")
            showTennantList(landlordId)
        }

/*        view.recyclerViewTennant.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, TennantDetail()).addToBackStack(null).commit()
            }
        })*/

        return view
    }

    fun showTennantList(landlordId: String){

        var apiInterface: ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var userCall = apiInterface.showTennantList(landlordId)

        userCall.enqueue(object : retrofit2.Callback<TenantList>{
            override fun onFailure(call: Call<TenantList>, t: Throwable) {
                Log.e("TEN LIST ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<TenantList>, response: Response<TenantList>) {
                Log.i("TEN LIST RES", response.body().toString())
/*
                var bundle = Bundle()
*/

                var tenantItems = response.body()

/*
                var tenantEmail = tenantItems!!.tennants[id].tenantemail
                var tenantMobile = tenantItems.tennants[id].tenantmobile
                var tenantName = tenantItems.tennants[id].tenantname

                bundle.putString("tenant_email", tenantEmail)
                bundle.putString("tenant_mobile", tenantMobile)
                bundle.putString("tenant_name", tenantName)
*/

                view?.recyclerViewTennant?.adapter = TennantAdapter(tenantItems!!.tennants, view!!.context )

            }
        })
    }

/*

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    // I don't know what this does, but I'm going to search for it later anyway
    // But if I have to make a guess...
    // I'd say RecyclerView can have its own Click Listener on its children?
    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {

        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {

            override fun onChildViewAttachedToWindow(view: View) {

                view?.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                view?.setOnClickListener(null)
            }

        })
    }
*/


}
