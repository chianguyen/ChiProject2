package org.mp.chiproject2.views.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_landing_l.*
import kotlinx.android.synthetic.main.activity_landing_t.*
import kotlinx.android.synthetic.main.fragment_tennant_detail.*
import kotlinx.android.synthetic.main.fragment_tennant_detail.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.views.activities.LandingActivityL

/**
 * A simple [Fragment] subclass.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"

class TennantDetail : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
            param5 = it.getString(ARG_PARAM5)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_tennant_detail, container, false)

        view.tenantdetail_container.animation = AnimationUtils.loadAnimation(context, R.anim.fate_transistion_anim2)

        view.personal_phone.text = "$param1"
        view.personal_email.text = "$param2"
        view.personal_address.text = "$param5"
        view.personal_name.text = param3
        Glide.with(view!!.context).load(param4.toString()).into(view.personal_img)
        Log.i("IMG CONTENT", param4.toString())

        view.personal_phone.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var iphone = Intent(Intent.ACTION_CALL, Uri.parse("tel:$param1"))
                startActivity(iphone)
            }
        })

        view.personal_email.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var iphone = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$param2"))
                startActivity(iphone)
            }
        })

        view.tenant_call.   setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var iphone = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$param1"))
                startActivity(iphone)
            }
        })

        view.tenant_text.   setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var iphone = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$param1"))
                startActivity(iphone)
            }
        })

        view.tenant_email.  setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var iphone = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$param2"))
                startActivity(iphone)
            }
        })


        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: String, param5: String) =
            TennantDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAM4, param4)
                    putString(ARG_PARAM5, param5)
                }
            }
    }

}
