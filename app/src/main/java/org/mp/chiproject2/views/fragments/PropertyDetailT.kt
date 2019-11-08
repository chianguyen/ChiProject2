package org.mp.chiproject2.views.fragments

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_property_detail.view.*
import kotlinx.android.synthetic.main.fragment_property_detail_t.*
import kotlinx.android.synthetic.main.fragment_property_detail_t.view.*

import org.mp.chiproject2.R
import org.mp.chiproject2.tools.DescriptionList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class PropertyDetailT : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    var desList = DescriptionList.descriptionList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_detail_t, container, false)

        view.propdetailT_container.animation = AnimationUtils.loadAnimation(context, R.anim.fate_transistion_anim2)

        var propAddress = param1
        var propPrice = param2
        var propImg = param3

        view.detailT_address.text = propAddress
        view.detailT_price.text = "$" + propPrice
        Glide.with(view!!.context).load(propImg.toString()).into(view.img_house_detailT)

        view.prop_descriptionT.text = desList[(0..11).random()]

        view.property_shareT.setOnClickListener(object : View.OnClickListener{
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
        fun newInstance(param1: String, param2: String, param3: String) =
            PropertyDetailT().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}
