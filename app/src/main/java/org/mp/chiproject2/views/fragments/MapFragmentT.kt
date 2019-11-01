package org.mp.chiproject2.views.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import org.mp.chiproject2.R
import org.mp.chiproject2.viewmodels.MapsTViewModel
import org.mp.chiproject2.views.activities.LandingActivityT

/**
 * A simple [Fragment] subclass.
 */
class MapFragmentT : Fragment(), OnMapReadyCallback {

    lateinit var mapModelT : MapsTViewModel
    private lateinit var landingActivityT: LandingActivityT


    override fun onAttach(context: Context) {
        super.onAttach(context)
        landingActivityT = context as LandingActivityT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_map_fragment_t, container, false)

        mapModelT = ViewModelProviders.of(landingActivityT).get(MapsTViewModel::class.java)

        var mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.mapT) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            onMapReady(it)
        })

        return view
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        var mMap = googleMap

        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID

        mapModelT.showPropertyTList().observe(this, Observer {
            var longT = it.first
            var latT = it.second
            var addressT = it.third

            for(i in 0 until longT.size){
                mMap.addMarker(MarkerOptions().position(LatLng(latT[i].toDouble(), longT[i].toDouble())).title("Property").snippet(addressT[i]))
            }
        })

        val chicago = LatLng(41.881832, -87.623177)
        mMap.addMarker(MarkerOptions().position(chicago).title("HERE"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chicago, 10.0f))


    }

}
