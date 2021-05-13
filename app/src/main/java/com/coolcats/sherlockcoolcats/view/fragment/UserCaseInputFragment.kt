package com.coolcats.sherlockcoolcats.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
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
import kotlinx.android.synthetic.main.fragment_user_case_input.*

class UserCaseInputFragment(private val caseLocationInputDelegate: CaseLocationInputDelegate) :
    Fragment(), LocationListener, GoogleMap.OnMapClickListener {

    //private class globals
    private lateinit var googleMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var userLocation: Location

    //delegate for retrieving latlng from input map
    interface CaseLocationInputDelegate {
        fun getLatLng(latLng: LatLng): LatLng
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
        return inflater.inflate(R.layout.fragment_user_case_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //gets map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        //button for searching a particular latlng
        loc_search_btn.setOnClickListener {
            val latLng = LatLng(
                lat_edit_txt.text.toString().toDouble(),
                long_edit_txt.text.toString().toDouble()
            )
            myLog("ME: Input Map Search Loc: $latLng")
            userLocation.longitude = latLng.longitude
            userLocation.latitude = latLng.latitude
            updateLocation(userLocation)
        }

        //button for submitting latlng and closing fragment
        submit_location_btn.setOnClickListener {
            val latlng = LatLng(userLocation.latitude, userLocation.longitude)
            myLog("ME: Input Map Submitting: $latlng")
            caseLocationInputDelegate.getLatLng(latlng)
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    //used to update latlng edittexts values
    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    //does nothing
    override fun onLocationChanged(location: Location) {
        //do nothing
    }

    //updates internal userLocation value
    private fun updateLocation(userLocation: Location) {
        this.userLocation = userLocation
        val latlng = LatLng(this.userLocation.latitude, this.userLocation.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        myLog("ME: Input Map updated to: $latlng")
    }

    //required operations at onStart()
    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            5f,
            this
        )
    }

    //behaviour for when user clicks on map
    override fun onMapClick(latlng: LatLng) {
        myLog("ME: Input Map Clicked: $latlng")
        googleMap.addMarker(MarkerOptions().position(latlng))
        lat_edit_txt.text = latlng.latitude.toString().toEditable()
        long_edit_txt.text = latlng.longitude.toString().toEditable()
        userLocation.latitude = latlng.latitude
        userLocation.longitude = latlng.longitude
        updateLocation(userLocation)
    }
}