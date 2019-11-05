package org.mp.chiproject2

import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.info_window.view.*
import org.mp.chiproject2.models.PropertyL
import org.mp.chiproject2.models.PropertyT
import org.mp.chiproject2.tools.ImgDatabase

class MapInfoWindowAdapter(val context: Fragment): GoogleMap.InfoWindowAdapter {

    var imgList = ImgDatabase.houseList

    private val contents = context.layoutInflater.inflate(R.layout.info_window, null)

    override fun getInfoContents(marker: Marker): View {
        contents.info_window_title.text = marker.title
        var landlordProp = marker.tag as? PropertyL
        var tenantProp   = marker.tag as? PropertyT

        if(landlordProp!=null){
            Glide.with(context).load(imgList[(0..12).random()]).into(contents.info_window_img)
        }
        else{
            Glide.with(context).load(imgList[(0..12).random()]).into(contents.info_window_img)
        }

        return contents

    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}