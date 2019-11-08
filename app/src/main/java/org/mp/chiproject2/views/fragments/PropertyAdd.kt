package org.mp.chiproject2.views.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.fragment.app.FragmentManager
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.common.reflect.Reflection.getPackageName
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_property_add.*
import kotlinx.android.synthetic.main.fragment_property_add.view.*
import kotlinx.android.synthetic.main.fragment_property_list.view.*
import okhttp3.ResponseBody

import org.mp.chiproject2.R
import org.mp.chiproject2.network.ApiClient
import org.mp.chiproject2.network.ApiInterface
import org.mp.chiproject2.views.activities.LandingActivityL
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.security.AccessController.checkPermission
import java.util.*

/**
 * A simple [Fragment] subclass.
 */

private const val PERMISSION_REQUEST = 10

class PropertyAdd : Fragment() {

/*    private var googleApiClient: GoogleApiClient? = null
    private var location: Location? = null
    private var locationRequest: LocationRequest? = null
    //private var geocoderHandler: GeocoderHandler = GeocoderHandler()
    private var isPermissionGranted: Boolean = false
    private var locationGps: Location? = null
    private var currentLocation: String? = null*/

    private var isPermissionGranted: Boolean = false

    lateinit var locationManager: LocationManager
    private var hasGps = false

    //object to store location
    private var locationGps: Location? = null

    //permission
    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    //Geocoder handler, used for getting the address
    private var geocoderHandler: GeocoderHandler = GeocoderHandler()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_property_add, container, false)

        var sp: SharedPreferences = view.context!!.getSharedPreferences("chamber", Context.MODE_PRIVATE)

        var userId = sp.getString("user_id", "DEFAULT")
        var userType = sp.getString("user_type", "DEFAULT")

        view.btn_prop_add.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                var addressA = property_add_address.text.toString()
                var cityA    = property_add_city.text.toString()
                var stateA   = property_add_state.text.toString()
                var countryA = property_add_country.text.toString()
                var prostatA = property_add_prostatus.text.toString()
                var mortageA = property_add_mortageinfo.text.toString()
                var priceA   = property_add_price.text.toString()

                if (userType != null && userId != null) {

                    var latitude = ""
                    var longitude = ""
                    var geocoderMatches: List<Address>? = null

                    try {
                        var fullAddress = "$addressA, $cityA, $stateA, $countryA"
                        geocoderMatches = Geocoder(view.context).getFromLocationName(fullAddress, 1)
                    }
                    catch (e: Exception){
                        Log.e("ERROR", e.toString())
                    }

                    if (geocoderMatches != null){
                        latitude = geocoderMatches[0].latitude.toString()
                        longitude = geocoderMatches[0].longitude.toString()

                        addProperty(addressA, cityA, stateA, countryA, prostatA, priceA, mortageA, userId, userType, latitude, longitude)

                        Log.i("LONGLAT", "lat = $latitude, long = $longitude")



                    }

                }

            }
        })
/*
        getPermission()
        buildGoogleApi()*/

        view.btn_prop_add_current.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
              /*  getLocation()*/

                getLocation()
              //  Toast.makeText(activity!!.applicationContext, "Location is: $currentLocation", Toast.LENGTH_SHORT).show()

            }
        })

        return view
    }

    fun addProperty(address: String, city: String, state: String,
                    country: String, proStat: String, price: String,
                    mortage: String, userid: String, usertype: String,
                    latitude: String, longitude: String) {

        var apiInterface : ApiInterface = ApiClient().onRetrofitCreate()!!.create(ApiInterface::class.java)

        var propCall = apiInterface.addProperty(address, city, state, country, proStat, price, mortage, userid, usertype, latitude, longitude)

        propCall.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("PROP ADD ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("PROP ADD RES", response.body()!!.string())
                Toast.makeText(view?.context, "Property added", Toast.LENGTH_SHORT).show()
                fragmentManager!!.beginTransaction().replace(R.id.main_frameL, PropertyListL()).addToBackStack(null).commit()
            }
        })


    }

    //get permission
    private fun getPermission() {

        Dexter.withActivity(activity).withPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
            .withListener(object : MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    //if permission granted,
                    // make boolean = true
                    if (report.areAllPermissionsGranted()) {
                        isPermissionGranted = true
                    }

                    //If any permission DENIED
                    if (report.isAnyPermissionPermanentlyDenied) {

                        //show an alert dialogue
                        val builder = AlertDialog.Builder(activity!!.applicationContext)
                        builder.setTitle("Need Permission")
                        builder.setMessage("We need your permission to access your current location. Please grant the access.")

                        //Positive button -> Go to settings
                        builder.setPositiveButton(
                            "Go to settings"
                        ) { dialog, which ->
                            //Go to settings
                            dialog.dismiss()
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity!!.getPackageName(), null)
                            intent.data = uri
                            startActivityForResult(intent, 101)
                        }

                        //Negative button -> Cancel, and show nothing.
                        builder.setNegativeButton(
                            "Cancel"
                        ) { dialog, which -> dialog.dismiss() }

                        //show the builder
                        builder.show()
                    }

                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).onSameThread().check()
    }

    //Get the location
    @SuppressLint("MissingPermission")
    private fun getLocation(){

        locationManager = activity!!.applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        //If there is gps
        if(hasGps){

            if(hasGps) {

                Log.d("CodeAndroidLocation", "hasGps")

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object :
                    android.location.LocationListener {

                    //Real time location udate
                    override fun onLocationChanged(location: Location?) {
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })

                //get last known location
                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                //if it's not null, make it the location we want
                if (localGpsLocation != null)
                    locationGps = localGpsLocation

            }

            if(locationGps!=null){
                //The commented code below will show the longitude and latitude as numbers
                /*  text_result.append("\nGPS ")
                    text_result.append("\nLatitude: " + locationGps!!.latitude)
                    text_result.append("\nLongitude: " + locationGps!!.longitude)
                */
                Log.d("CodeAndroidLocation", " GPS Latitude: " + locationGps!!.latitude)
                Log.d("CodeAndroidLocation", " GPS Longitude: " + locationGps!!.longitude)

                //The Handler already set the text_result view the address.
                getAddressFromLocation(locationGps!!, activity!!.applicationContext,  geocoderHandler)

            }

        } else {
            //if there's no gps, go to the settings
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

    }

    //Get address from the location
    fun getAddressFromLocation(location: Location, context: Context, handler: Handler) {

        val thread = object : Thread() {

            override fun run() {

                val geocoder = Geocoder(context, Locale.getDefault())

                //result store the address
                var result: String? = null

                try {
                    val list = geocoder.getFromLocation(
                        location.latitude, location.longitude, 1
                    )
                    if (list != null && list.size > 0) {
                        val address = list[0]

                        // sending back first address line and locality
                        result = address.getAddressLine(0)
                        //+ ", " + address.locality
                    }
                }

                catch (e: IOException) {
                    Log.e("error", "Impossible to connect to Geocoder", e)
                }

                finally {
                    val msg = Message.obtain()
                    msg.setTarget(handler)
                    if (result != null) {
                        msg.what = 1
                        val bundle = Bundle()
                        bundle.putString("address", result)
                        msg.setData(bundle)
                    } else
                        msg.what = 0
                    msg.sendToTarget()
                }
            }
        }
        thread.start()
    }


    @SuppressLint("HandlerLeak")    //To handle leaks. generates if you don't write it
    //click on the GeocoderHandler
    private inner class GeocoderHandler : Handler() {

        override fun handleMessage(message: Message) {

            var sp: SharedPreferences = view!!.context.getSharedPreferences("chamber", Context.MODE_PRIVATE)

            var userId = sp.getString("user_id", "DEFAULT")
            var userType = sp.getString("user_type", "DEFAULT")

            val result: String?

            when (message.what) {
                1 -> {
                    val bundle = message.data
                    result = bundle.getString("address")
                }
                else -> result = null
            }

            var fullAdd = result!!.split(",").toTypedArray()

            var pPrice = property_add_price.text.toString()

            addProperty(fullAdd[0],fullAdd[1], fullAdd[2], fullAdd[3], "tenants", pPrice, "no", userId!!, userType!!, locationGps!!.latitude.toString(), locationGps!!.longitude.toString())

            Log.i("SELF ADDRESS: ", result.toString())
            Log.i("SELF PRICE: ", pPrice)
            Log.i("SELF ID: ", userId)
            Log.i("SELF TYPE: ", userType)
            Log.i("SELF LAT: ", locationGps!!.latitude.toString())
            Log.i("SELF LONG: ", locationGps!!.longitude.toString())


        }
    }

/*
    //get permission
    private fun getPermission() {

        Dexter.withActivity(activity).withPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
            .withListener(object : MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    //if permission granted,
                    // make boolean = true
                    if (report.areAllPermissionsGranted()) {
                        isPermissionGranted = true
                    }

                    //If any permission DENIED
                    if (report.isAnyPermissionPermanentlyDenied) {

                        //show an alert dialogue
                        val builder = AlertDialog.Builder(activity!!.applicationContext)
                        builder.setTitle("Need Permission")
                        builder.setMessage("We need your permission to access your current location. Please grant the access.")

                        //Positive button -> Go to settings
                        builder.setPositiveButton(
                            "Go to settings"
                        ) { dialog, which ->
                            //Go to settings
                            dialog.dismiss()
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity!!.getPackageName(), null)
                            intent.data = uri
                            startActivityForResult(intent, 101)
                        }

                        //Negative button -> Cancel, and show nothing.
                        builder.setNegativeButton(
                            "Cancel"
                        ) { dialog, which -> dialog.dismiss() }

                        //show the builder
                        builder.show()
                    }

                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).onSameThread().check()
    }

    //Built Google API client
    private fun buildGoogleApi() {

        //Built the client
        googleApiClient = GoogleApiClient.Builder(activity!!.applicationContext)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
    }

    override fun onStart() {
        super.onStart()
        if (googleApiClient != null) {
            googleApiClient!!.connect()
        }
    }

    private fun getLocation() {

        locationRequest = LocationRequest()
        locationRequest!!.setInterval(1000)

        if (isPermissionGranted) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onConnected(bundle:Bundle?) {
        //if permission is granted
        // connect!

        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)

        if (location != null)
        {
            Log.i("PROP LIVE LOCATION ADD", "Latitude: " + location!!.getLatitude()
                    + " Longitude: " + location!!.getLongitude())
            getAddressFromLocation(location!!.getLatitude(), location!!.getLongitude())

        }
        else
        {
            Toast.makeText(activity!!.applicationContext, "Location is null", Toast.LENGTH_SHORT).show()
        }

        getLocation()
    }

    override fun onConnectionSuspended(i:Int) {

    }


    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun onLocationChanged(location:Location?) {

    }*/

/*    //Get address from the location
    fun getAddressFromLocation(location: Location, context: Context, handler: Handler) {

        val thread = object : Thread() {

            override fun run() {

                val geocoder = Geocoder(context, Locale.getDefault())

                //result store the address
                var result: String? = null

                try {
                    val list = geocoder.getFromLocation(
                        location.latitude, location.longitude, 1
                    )
                    if (list != null && list.size > 0) {
                        val address = list[0]

                        // sending back first address line and locality
                        result = address.getAddressLine(0)
                        //+ ", " + address.locality
                    }
                }

                catch (e: IOException) {
                    Log.e("error", "Impossible to connect to Geocoder", e)
                }

                finally {
                    val msg = Message.obtain()
                    msg.setTarget(handler)
                    if (result != null) {
                        msg.what = 1
                        val bundle = Bundle()
                        bundle.putString("address", result)
                        msg.setData(bundle)
                    } else
                        msg.what = 0
                    msg.sendToTarget()
                }
            }
        }
        thread.start()
    }


    //Geocoder
    @SuppressLint("HandlerLeak")    //To handle leaks. generates if you don't write it
    //click on the GeocoderHandler
    private inner class GeocoderHandler : Handler() {

        override fun handleMessage(message: Message) {

            val result: String?

            when (message.what) {
                1 -> {
                    val bundle = message.data
                    result = bundle.getString("address")
                }
                else -> result = null
            }
            currentLocation = result
            Log.i("CURRENT LOCATION IS", result.toString())
        }
    }*/

 /*    fun getAddressFromLocation(latitude: Double, longitude: Double) {

        var geocoder = Geocoder(activity!!.applicationContext, Locale.ENGLISH);


        try {
            var addresses : List<Address> = geocoder.getFromLocation(latitude, longitude, 1)

            if (addresses.size > 0) {
                var fetchedAddress: Address = addresses.get(0)
                var strAddress: StringBuilder = StringBuilder()

                for(i in 0 until fetchedAddress.maxAddressLineIndex){
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ")
                }

                Log.i("STREET IS: ", strAddress.toString())

            } else {
                Log.i("STREET IS: ", "Searching Current Address")
            }

        } catch (e: IOException) {
            e.printStackTrace();
            Log.i("CAN'T GET LOC","Could not get address..!")
        }
    }*/

}
