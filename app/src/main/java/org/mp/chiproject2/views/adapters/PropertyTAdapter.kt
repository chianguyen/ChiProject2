package org.mp.chiproject2.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyT
import org.mp.chiproject2.tools.ImgDatabase
import org.mp.chiproject2.views.activities.LandingActivityT
import org.mp.chiproject2.views.fragments.PropertyDetailT

class  PropertyTAdapter(var propertyTList: List<PropertyT>, val context: Context): RecyclerView.Adapter<PropertyTAdapter.ViewHolder1>() {

    var imgList = ImgDatabase.houseList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        var v = LayoutInflater.from(context).inflate(R.layout.propertyt_unit, parent, false)
        return ViewHolder1(v)
    }

    override fun getItemCount(): Int {
        return propertyTList.size
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        var propData        = propertyTList.get(position)
        holder.txtPropTAddress.text  = propData.propertyaddress
        holder.txtPropTCity.text     = propData.propertycity + ", "
        holder.txtPropTState.text    = propData.propertystate + ", "
        holder.txtPropTCountry.text  = propData.propertycountry + ", "
        holder.txtPropTPrice.text    = "Price: $${propData.propertypurchaseprice}"

        Glide.with(context).load(imgList[position%imgList.size]).into(holder.propImg)

        //animation
        holder.propImg.animation = AnimationUtils.loadAnimation(context, R.anim.fate_transistion_anim)
        holder.container.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_anim)

    }

    inner class ViewHolder1(view: View): RecyclerView.ViewHolder(view){
        var txtPropTAddress = view.findViewById<TextView>(R.id.propT_text_address)
        var txtPropTCity    = view.findViewById<TextView>(R.id.propT_text_city)
        var txtPropTState   = view.findViewById<TextView>(R.id.propT_text_state)
        var txtPropTCountry = view.findViewById<TextView>(R.id.propT_text_country)
        var txtPropTPrice   = view.findViewById<TextView>(R.id.propT_text_price)
        var propImg       = view.findViewById<ImageView>(R.id.propT_img)
        var container          = view.findViewById<View>(R.id.propertyT_container)


        init {
            view.setOnClickListener {
                var propertyTitem = propertyTList[adapterPosition]

                var fullAddress1 =
                    "${propertyTitem.propertyaddress}\n" + "${propertyTitem.propertycity}, " +
                            "${propertyTitem.propertystate}, ${propertyTitem.propertycountry}"

                var propDetailT = PropertyDetailT.newInstance(fullAddress1, propertyTitem.propertypurchaseprice, imgList[adapterPosition%imgList.size]
                )

                (context as LandingActivityT).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frameT, propDetailT).addToBackStack(null).commit()

            }
        }

    }

}