package org.mp.chiproject2.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tennant_list.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.adapters.TennantAdapter
import org.mp.chiproject2.models.Tennant

/**
 * A simple [Fragment] subclass.
 */
class TennantListFragment : Fragment() {

    var tennantList : ArrayList<Tennant> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_tennant_list, container, false)

        view.recyclerViewTennant.layoutManager = LinearLayoutManager(context)


        view.recyclerViewTennant.adapter = TennantAdapter(tennantList, view.context)





        return view
    }


}
