package org.mp.chiproject2.views.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_landing_l.*
import kotlinx.android.synthetic.main.activity_landing_t.*
import kotlinx.android.synthetic.main.fragment_property_list.*
import kotlinx.android.synthetic.main.fragment_property_list.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyLList
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.views.activities.LandingActivityL
import org.mp.chiproject2.views.adapters.PropertyLAdapter
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PropertyListL : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_list, container, false)

        var sp: SharedPreferences = view.context!!.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        var userId = sp.getString("user_id", "DEFAULT")
        var userType = sp.getString("user_type", "DEFAULT")

        //Try this for fun
/*    https://github.com/futuresimple/android-floating-action-button    */

        //This is the button that triggers the appear/disappear
        view.btn_fab.setOnClickListener(object: View.OnClickListener{

            @SuppressLint("RestrictedApi")
            override fun onClick(v: View?) {
                //If they're NOT visible, make them visible
                if (!view.btn_proplist_add.isVisible) {
                    view.btn_proplist_add.visibility = View.VISIBLE
                    view.btn_proplist_rmv.visibility = View.VISIBLE
                }
                //If they ARE visible, make them invisible
                else{
                    view.btn_proplist_add.visibility = View.INVISIBLE
                    view.btn_proplist_rmv.visibility = View.INVISIBLE
                }
            }
        })



        view.recyclerViewPropL.layoutManager = LinearLayoutManager(view.context)
        //     view.recyclerViewProp.adapter = PropertyLAdapter(propList, view.context)

        if (userId != null && userType!= null) {
            Log.i("PROP INFO", "Calling Id: $userId and user type: $userType")

            showPropertyLList(userId, userType)

/*            view.recyclerViewPropL.addOnItemClickListener(object : OnItemClickListener{
                override fun onItemClicked(position: Int, view: View) {

                }
            })*/
        }

        view.btn_proplist_add.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, ProplistAddMedium()).addToBackStack(null).commit()
            }

        })

        view.btn_proplist_rmv.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, PropertyRmv()).addToBackStack(null).commit()
            }
        })

        return view
    }


    fun showPropertyLList(userID: String, userType: String){

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var propCall = apiInterface.showPropertyLList(userID, userType)

        propCall.enqueue(object: retrofit2.Callback<PropertyLList>{
            override fun onFailure(call: Call<PropertyLList>, t: Throwable) {
                Log.e("PROP L ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<PropertyLList>, response: Response<PropertyLList>) {
                Log.i("PROP L RES", response.body().toString())

                var propertyLitems = response.body()

                Log.i("PROP SIZE", propertyLitems!!.propertiesL.size.toString())

                view?.recyclerViewPropL?.adapter = PropertyLAdapter(propertyLitems.propertiesL, view!!.context)

            }
        })

    }

/*    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {

        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }

            }

        })
    }*/


}
