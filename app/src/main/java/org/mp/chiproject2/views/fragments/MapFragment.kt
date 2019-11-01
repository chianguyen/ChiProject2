package org.mp.chiproject2.views.fragments


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.mp.chiproject2.R
import org.mp.chiproject2.models.PropertyL
import org.mp.chiproject2.viewmodels.MapsViewModel
import org.mp.chiproject2.views.activities.LandingActivityL

/**
 * A simple [Fragment] subclass.
 **/
class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var mapModel : MapsViewModel
    private lateinit var landingActivityL: LandingActivityL

    override fun onAttach(context: Context) {
        super.onAttach(context)
        landingActivityL = context as LandingActivityL
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_map, container, false)

        mapModel = ViewModelProviders.of(landingActivityL).get(MapsViewModel::class.java)

        var mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            onMapReady(it)
        })

        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        var mMap = googleMap

        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMap.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(p0: Marker?) {
                var propertyD = p0?.tag as PropertyL
                var propDetail = PropertyDetail.newInstance(propertyD.propertyaddress, propertyD.propertycity, "", propertyD.id)
                (context as LandingActivityL).supportFragmentManager!!.beginTransaction().replace(R.id.main_frameL, propDetail).addToBackStack(null).commit()

            }

        })

        mapModel.showPropertyLList().observe(this, Observer {
            /*            var long = it.first
                        var lat = it.second
                        var address = it.third*/

//            for(i in 0 until long.size){
            for(i in 0 until it.size){

                var marker = mMap.addMarker(MarkerOptions().position(LatLng(it[i].propertylatitude.toDouble(), it[i].propertylongitude.toDouble())).title(it[i].propertyaddress).snippet(it[i].propertystate))
                //   mMap.addMarker(MarkerOptions().position(LatLng(lat[i].toDouble(), long[i].toDouble())).title("Property").snippet(address[i]))
                //   mMap.addMarker()

                //contain whole object
                marker.tag = it[i]

                mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter{
                    override fun getInfoContents(p0: Marker?): View {
                        var info = LinearLayout(view?.context)
                        info.orientation = LinearLayout.VERTICAL

                        var title = TextView(view?.context)
                        title.setTextColor(Color.BLUE)
                        title.gravity = Gravity.CENTER
                        title.setTypeface(null, Typeface.BOLD)
                        title.setText(p0?.title)

                        var snippet = TextView(view?.context)
                        snippet.setTextColor(Color.GRAY)
                        snippet.setText(p0?.snippet)

                        info.addView(title)
                        info.addView(snippet)

                        return info
                    }

                    override fun getInfoWindow(p0: Marker?): View? {
                        return null
                    }

                })

                mMap.setOnMapClickListener {  }
            }
        })

        val chicago = LatLng(41.881832, -87.623177)
        mMap.addMarker(MarkerOptions().position(chicago).title("Chicago"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chicago, 10.0f))


    }



}

