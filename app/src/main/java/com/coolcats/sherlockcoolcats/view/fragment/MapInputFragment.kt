package com.coolcats.sherlockcoolcats.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.util.myLog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapInputFragment(private val mapInputDelegate: MapInputDelegate) :
    Fragment(), LocationListener, GoogleMap.OnMapClickListener {

    //private class globals
    private lateinit var googleMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private var userLocation: LatLng? = null

    //    //delegate for retrieving latlng from input map
    interface MapInputDelegate {
        fun getLatLng(latLng: LatLng)
    }

    fun clearPin(){
        googleMap.clear()
    }

    //required for initializing input map
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        this.googleMap.setOnMapClickListener(this)
        myLog("ME: Input Map is Ready")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_input_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //gets map
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.input_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    //used to update latlng edittexts values
//    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    //does nothing
    override fun onLocationChanged(location: Location) {
        //do nothing
    }

    //updates internal userLocation value
    private fun updateLocation(userLocation: LatLng) {
        this.userLocation = userLocation
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
        mapInputDelegate.getLatLng(this.userLocation!!)
        myLog("ME: Input Map updated to: $userLocation")
    }

/*    //required operations at onStart()
    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            5f,
            this
        )
    }*/

    //behaviour for when user clicks on map
    override fun onMapClick(latlng: LatLng) {
        myLog("ME: Input Map Clicked: $latlng")
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(latlng))
        updateLocation(latlng)
    }
}