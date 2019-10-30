package org.mp.chiproject2.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyT

class PropertyTAdapter(var propertyTList: List<PropertyT>, val context: Context): RecyclerView.Adapter<PropertyTAdapter.ViewHolder1>() {

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
        holder.txtPropTCity.text     = propData.propertycity
        holder.txtPropTState.text    = propData.propertystate
        holder.txtPropTCountry.text  = propData.propertycountry
        holder.txtPropTPrice.text    = propData.propertypurchaseprice
        holder.txtPropTID.text       = propData.id
    }

    inner class ViewHolder1(view: View): RecyclerView.ViewHolder(view){
        var txtPropTAddress = view.findViewById<TextView>(R.id.propT_text_address)
        var txtPropTCity    = view.findViewById<TextView>(R.id.propT_text_city)
        var txtPropTState   = view.findViewById<TextView>(R.id.propT_text_state)
        var txtPropTCountry = view.findViewById<TextView>(R.id.propT_text_country)
        var txtPropTPrice   = view.findViewById<TextView>(R.id.propT_text_price)
        var txtPropTID      = view.findViewById<TextView>(R.id.propT_text_id)
    }

}