package org.mp.chiproject2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mp.chiproject2.R
import org.mp.chiproject2.models.Tennant

class TennantAdapter(var tennantList : ArrayList<Tennant>, val context: Context): RecyclerView.Adapter<TennantAdapter.ViewHolder2>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        var v = LayoutInflater.from(context).inflate(R.layout.tennant_unit, parent, false)
        return ViewHolder2(v)
    }

    override fun getItemCount(): Int {
        return tennantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ViewHolder2(view: View): RecyclerView.ViewHolder(view){

    }

}
