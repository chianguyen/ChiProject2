package org.mp.chiproject2.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_terms.view.*

import org.mp.chiproject2.R

/**
 * A simple [Fragment] subclass.
 */
class Terms : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_terms, container, false)

        view.btn_terms_back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                fragmentManager!!.popBackStack()
            }
        })

        return view
    }


}
