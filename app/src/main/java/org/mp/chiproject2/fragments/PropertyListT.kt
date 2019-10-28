package org.mp.chiproject2.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_property_list.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.adapters.PropertyAdapter
import org.mp.chiproject2.models.Property

/**
 * A simple [Fragment] subclass.
 */
class PropertyListT : Fragment() {

    var propList : ArrayList<Property> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_list_t, container, false)

        view.recyclerViewProp.layoutManager = LinearLayoutManager(view.context)

        view.recyclerViewProp.adapter = PropertyAdapter(propList, view.context)


        return view
    }


}
