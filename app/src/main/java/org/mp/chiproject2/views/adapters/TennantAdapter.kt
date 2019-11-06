package org.mp.chiproject2.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.mp.chiproject2.R
import org.mp.chiproject2.models.Tenant
import org.mp.chiproject2.tools.ImgDatabase
import org.mp.chiproject2.views.activities.LandingActivityL
import org.mp.chiproject2.views.fragments.TennantDetail

class TennantAdapter(var tenantList : List<Tenant>, val context: Context): RecyclerView.Adapter<TennantAdapter.ViewHolder2>()  {

    var imgList = ImgDatabase.avatarList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        var v = LayoutInflater.from(context).inflate(R.layout.tennant_unit, parent, false)
        return ViewHolder2(v)
    }

    override fun getItemCount(): Int {
        return tenantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        var tenantData        = tenantList.get(position)
     //   holder.txtTenantID.text      = tenantData.id
        holder.txtTenantName.text    = tenantData.tenantname
        holder.txtTenantEmail.text   = "Email: ${tenantData.tenantemail}"
       // holder.txtTenantAddress.text = "Address: ${tenantData.tenantaddress}"
        holder.txtTenantMobile.text  = "Mobile: ${tenantData.tenantmobile}"
        holder.txtTenantPropID.text  = "Property ID: ${tenantData.propertyid}"
        Glide.with(context).load(imgList[position%imgList.size]).into(holder.imgTenant)
    }

    inner class ViewHolder2(view: View): RecyclerView.ViewHolder(view){
       // var txtTenantID     = view.findViewById<TextView>(R.id.tenant_list_id)
        var txtTenantName   = view.findViewById<TextView>(R.id.tenant_list_name)
        var txtTenantEmail  = view.findViewById<TextView>(R.id.tenant_list_email)
     //   var txtTenantAddress= view.findViewById<TextView>(R.id.tenant_list_address)
        var txtTenantMobile = view.findViewById<TextView>(R.id.tenant_list_mobile)
        var txtTenantPropID = view.findViewById<TextView>(R.id.tenant_list_propID)
        var imgTenant      = view.findViewById<ImageView>(R.id.tenant_photo)

        init {
            view.setOnClickListener{

                var tenantItem =tenantList[adapterPosition%tenantList.size]
                Log.i("TENNANT NAME", tenantItem.tenantname)

                var fullAddress = tenantItem.tenantaddress

                var tenantFrag = TennantDetail.newInstance(tenantItem.tenantmobile, tenantItem.tenantemail, tenantItem.tenantname, imgList[adapterPosition%imgList.size], fullAddress)
                (context as LandingActivityL).supportFragmentManager.beginTransaction().replace(R.id.main_frameL, tenantFrag).addToBackStack(null).commit()

            }
        }

    }

}
