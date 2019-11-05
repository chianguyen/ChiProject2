package org.mp.chiproject2.views.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_property_detail.view.*
import kotlinx.android.synthetic.main.fragment_tennant_detail.view.*
import okhttp3.ResponseBody

import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"

class PropertyDetail : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_detail, container, false)

        var propAddress = param1
        var propPrice = param2
        var propImg = param3
        var propID = param4

        view.detail_address.text = param1
        view.detail_price.text = "$" + param2
        Glide.with(view!!.context).load(param3.toString()).into(view.img_house_detail)
        Log.i("DETAIL IMG URL", param3.toString())

        view.detail_delete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val builder = AlertDialog.Builder(view.context)

                // Set the alert dialog title
                builder.setTitle("Property Removing")

                // Display a message on alert dialog
                builder.setMessage("Are you sure you want to remove this property?")

                builder.setPositiveButton("YES"){dialog, which ->
                    if (propID != null) {
                        rmvProperty(propID)
                    }
                }

                builder.setNegativeButton("NO"){dialog, which ->

                }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()

            }
        })

        view.property_share.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Share Property")
                    putExtra(Intent.EXTRA_TEXT, "Check out this property!\n$propAddress")
                }
                startActivity(intent)
            }
        })

        return view
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: String) =
            PropertyDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAM4, param4)
                }
            }
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
                Toast.makeText(view?.context, "Property removed", Toast.LENGTH_SHORT).show()
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, PropertyListL()).addToBackStack(null).commit()
            }
        })


    }

}
