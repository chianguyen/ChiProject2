package org.mp.chiproject2.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.activities.LandingActivityL
import org.mp.chiproject2.activities.LandingActivityT

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        view.login_btn_login.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var i = Intent(view.context, LandingActivityL::class.java)
                startActivity(i)
            }
        })

        view.login_btn_register.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var regFrag = RegisterFragment()
                fragmentManager!!.beginTransaction().replace(R.id.login_act, regFrag).addToBackStack(null).commit()
            }
        })

        view.login_btn_forgot.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var forgotFrag = ForgotPwdFragment()
                fragmentManager!!.beginTransaction().replace(R.id.login_act, forgotFrag).addToBackStack(null).commit()
            }
        })

        return view
    }


}
