package org.mp.chiproject2.views.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_property_list.view.*
import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyL
import org.mp.chiproject2.models.PropertyLList
import org.mp.chiproject2.tools.ColorDB
import org.mp.chiproject2.tools.ImgDatabase
import org.mp.chiproject2.views.activities.LandingActivityL
import org.mp.chiproject2.views.fragments.PropertyDetail

class PropertyLAdapter(var propertyLList: List<PropertyL>, val context: Context): RecyclerView.Adapter<PropertyLAdapter.ViewHolder>() {

    //singleton class
    var imgList = ImgDatabase.houseList
    var colorList = arrayOf(R.color.list_color1, R.color.list_color2, R.color.list_color3, R.color.list_color4,
        R.color.list_color5, R.color.list_color6, R.color.list_color7, R.color.list_color8,
        R.color.list_color9, R.color.list_color10, R.color.list_color11, R.color.list_color12,
        R.color.list_color13, R.color.list_color14, R.color.list_color15, R.color.list_color16)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.propertyl_unit, parent, false)

        //v.setBackgroundColor(ContextCompat.getColor(context, colorList[(0..15).random()]))

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return propertyLList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var propData        = propertyLList.get(position)
        holder.txtPropAddress.text   = propData.propertyaddress
        holder.txtPropCity.text      = propData.propertycity + ", "
        holder.txtPropState.text     = propData.propertystate + ", "
        holder.txtPropCountry.text   = propData.propertycountry + ", "
        holder.txtPropPrice.text     = "Price: $${propData.propertypurchaseprice}"
        holder.txtPropID.text        = "${propData.id}"

        //% to wrap
        var requestOption = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context).load(imgList[position%imgList.size]).apply(requestOption).into(holder.propImg)

        //================================
        holder.propImg.animation = AnimationUtils.loadAnimation(context, R.anim.fate_transistion_anim)
        holder.container.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_anim)

//        holder.container.setBackgroundColor(Color.parseColor("#${colorList[(0..15).random()]}"))

        var item_bg = ContextCompat.getColor(context, colorList[position%16])

//        holder.container.setBackgroundColor(colorList[position%16])

        holder.viewX.setBackgroundColor(item_bg)

        //================================

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var txtPropAddress = view.findViewById<TextView>(R.id.prop_text_address)
        var txtPropCity    = view.findViewById<TextView>(R.id.prop_text_city)
        var txtPropState   = view.findViewById<TextView>(R.id.prop_text_state)
        var txtPropCountry = view.findViewById<TextView>(R.id.prop_text_country)
        var txtPropPrice   = view.findViewById<TextView>(R.id.prop_text_price)
        var txtPropID      = view.findViewById<TextView>(R.id.prop_text_id)
        var propImg       = view.findViewById<ImageView>(R.id.prop_img)
        var container         = view.findViewById<View>(R.id.property_holder)

        var viewX = view

        init {
            view.setOnClickListener{
                var property1 = propertyLList[adapterPosition]

                var fullAddress1 = "${property1.propertyaddress}\n" + "${property1.propertycity}, " +
                        "${property1.propertystate}, ${property1.propertycountry}"

                var propDetail = PropertyDetail.newInstance(
                    fullAddress1,property1.propertypurchaseprice, imgList[adapterPosition], property1.id)

                (context as LandingActivityL).supportFragmentManager.beginTransaction().replace(R.id.main_frameL, propDetail).addToBackStack(null).commit()

            }
        }

    }
}