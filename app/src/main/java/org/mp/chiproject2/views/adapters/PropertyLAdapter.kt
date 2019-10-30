package org.mp.chiproject2.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyL
import org.mp.chiproject2.models.PropertyLList

class PropertyLAdapter(var propertyLList: List<PropertyL>, val context: Context): RecyclerView.Adapter<PropertyLAdapter.ViewHolder>() {

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
        holder.txtPropCity.text      = propData.propertycity
        holder.txtPropState.text     = propData.propertystate
        holder.txtPropCountry.text   = propData.propertycountry
        holder.txtPropPrice.text     = propData.propertypurchaseprice
        holder.txtPropID.text        = propData.id

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var txtPropAddress = view.findViewById<TextView>(R.id.prop_text_address)
        var txtPropCity    = view.findViewById<TextView>(R.id.prop_text_city)
        var txtPropState   = view.findViewById<TextView>(R.id.prop_text_state)
        var txtPropCountry = view.findViewById<TextView>(R.id.prop_text_country)
        var txtPropPrice   = view.findViewById<TextView>(R.id.prop_text_price)
        var txtPropID      = view.findViewById<TextView>(R.id.prop_text_id)
    }
}