package com.coolcats.sherlockcoolcats.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.view.adapter.CaseAdapter

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_case_locations.*

class CaseLocationsFragment : Fragment(), LocationListener, CaseAdapter.SolvedCaseDelegate {

    private lateinit var googleMap: GoogleMap

    private lateinit var locationManager: LocationManager

    private val adapter = CaseAdapter(this)

    private val callback = OnMapReadyCallback { googleMap ->
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        Log.d("TAG_X", "MapReady...!")
        this.googleMap = googleMap
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_case_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_location.setOnClickListener {
            updateLocation(userLocation)
        }

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        case_recyclerview.adapter = adapter
        val sHelper = LinearSnapHelper().also {
            it.attachToRecyclerView(case_recyclerview)
        }

        val list = mutableListOf<Case>(

        )

        adapter.caseList = list
    }

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
    private lateinit var userLocation: Location

    private fun updateLocation(userLocation: Location) {
        this.userLocation = userLocation
        my_location.visibility = View.VISIBLE
        val latLng = LatLng(userLocation.latitude, userLocation.longitude)
        googleMap.addMarker(MarkerOptions().position(latLng).title("Sherlock"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        //Log.d("TAG_X", "MapReady...!")
        myLog("MapReady...!")
    }

    override fun onLocationChanged(p0: Location) {
        updateLocation(p0)
    }

    override fun openSolvedCase(case: Case) {
        Toast.makeText(requireContext(), case.caseTitle, Toast.LENGTH_SHORT).show()
        navigateToAndMark(case)
    }

    private fun navigateToAndMark(case: Case) {
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(case.latLng).title(case.caseTitle))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(case.latLng))
    }
}