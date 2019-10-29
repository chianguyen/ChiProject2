package org.mp.chiproject2.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.activities.LoginActivity

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.btn_logout.setOnClickListener {
            var i = Intent(view.context, LoginActivity::class.java)
            startActivity(i)
        }


/*
        view.btn_emergency_call.setOnClickListener{
            var emergency = Intent(Intent.ACTION_CALL, Uri.parse("911"))
            startActivity(emergency)
        }
*/


        return view
    }


}
