package org.mp.chiproject2.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_landing_l.*
import kotlinx.android.synthetic.main.activity_landing_t.*
import kotlinx.android.synthetic.main.fragment_proplist_add_medium.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.views.activities.LandingActivityL

/**
 * A simple [Fragment] subclass.
 */
class ProplistAddMedium : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_proplist_add_medium, container, false)

        view.btn_prop_add_prop.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, PropertyAdd()).addToBackStack(null).commit()            }
        })

        view.btn_prop_add_tenant.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, TennantAdd()).addToBackStack(null).commit()
            }
        })

        return view
    }


}
