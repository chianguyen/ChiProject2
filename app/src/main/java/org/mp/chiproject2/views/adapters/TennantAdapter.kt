package org.mp.chiproject2.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.mp.chiproject2.R
import org.mp.chiproject2.models.Tenant

class TennantAdapter(var tenantList : List<Tenant>, val context: Context): RecyclerView.Adapter<TennantAdapter.ViewHolder2>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        var v = LayoutInflater.from(context).inflate(R.layout.tennant_unit, parent, false)
        return ViewHolder2(v)
    }

    override fun getItemCount(): Int {
        return tenantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        var tenantData        = tenantList.get(position)
        holder.txtTenantID.text      = tenantData.id
        holder.txtTenantName.text    = tenantData.tenantname
        holder.txtTenantEmail.text   = tenantData.tenantemail
        holder.txtTenantAddress.text = tenantData.tenantaddress
        holder.txtTenantMobile.text  = tenantData.tenantmobile
        holder.txtTenantPropID.text  = tenantData.propertyid

    }

    inner class ViewHolder2(view: View): RecyclerView.ViewHolder(view){
        var txtTenantID     = view.findViewById<TextView>(R.id.tenant_list_id)
        var txtTenantName   = view.findViewById<TextView>(R.id.tenant_list_name)
        var txtTenantEmail  = view.findViewById<TextView>(R.id.tenant_list_email)
        var txtTenantAddress= view.findViewById<TextView>(R.id.tenant_list_address)
        var txtTenantMobile = view.findViewById<TextView>(R.id.tenant_list_mobile)
        var txtTenantPropID = view.findViewById<TextView>(R.id.tenant_list_propID)
    }

}
