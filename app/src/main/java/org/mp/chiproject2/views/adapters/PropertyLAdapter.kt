package org.mp.chiproject2.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyL
import org.mp.chiproject2.models.PropertyLList
import org.mp.chiproject2.tools.ImgDatabase
import org.mp.chiproject2.views.activities.LandingActivityL
import org.mp.chiproject2.views.fragments.PropertyDetail

class PropertyLAdapter(var propertyLList: List<PropertyL>, val context: Context): RecyclerView.Adapter<PropertyLAdapter.ViewHolder>() {

    //singleton class
    var imgList = ImgDatabase.houseList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.propertyl_unit, parent, false)
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
        //Glide.with(context).load(imgList[position]).into(holder.propImg)

        //% to wrap
        Glide.with(context).load(imgList[position%imgList.size]).into(holder.propImg)

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var txtPropAddress = view.findViewById<TextView>(R.id.prop_text_address)
        var txtPropCity    = view.findViewById<TextView>(R.id.prop_text_city)
        var txtPropState   = view.findViewById<TextView>(R.id.prop_text_state)
        var txtPropCountry = view.findViewById<TextView>(R.id.prop_text_country)
        var txtPropPrice   = view.findViewById<TextView>(R.id.prop_text_price)
        var txtPropID      = view.findViewById<TextView>(R.id.prop_text_id)
        var propImg       = view.findViewById<ImageView>(R.id.prop_img)

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