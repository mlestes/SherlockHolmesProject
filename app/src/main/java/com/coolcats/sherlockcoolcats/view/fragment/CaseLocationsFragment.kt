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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.view.adapter.SolvedCaseAdapter
import com.coolcats.sherlockcoolcats.view.adapter.UnsolvedCaseAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_case_locations.*


class CaseLocationsFragment : Fragment(), LocationListener, SolvedCaseAdapter.SolCaseDelegate,
    UnsolvedCaseAdapter.UnSolvedCaseDelegate {


    private lateinit var googleMap: GoogleMap

    private lateinit var locationManager: LocationManager

    private lateinit var adapter: SolvedCaseAdapter

    // private lateinit var  unsolvedAdapter : UnsolvedCaseAdapter
    private val solveList = ArrayList<Case>()


    private val callback = OnMapReadyCallback { googleMap ->
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        Log.d("TAG_X", "MapReady...!")
        this.googleMap = googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_case_locations, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_location.setOnClickListener {
            updateLocation(userLocation)
        }

        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


        adapter = SolvedCaseAdapter(solveList)
        //  unsolvedAdapter = UnsolvedCaseAdapter(solveList)


        case_recyclerview.adapter = adapter


        //    unsolved_recyclerview.adapter = unsolvedAdapter


//        val list = mutableListOf<Case>(
//
//            Case( 1,"w",LatLng(-34.0, 151.0), false)
//
//            )
//        unsolvedAdapter.updateList(list)


    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            5f,
            this
        )
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(this)
    }

    private lateinit var userLocation: Location

    private fun updateLocation(userLocation: Location) {
        this.userLocation = userLocation
        my_location.visibility = View.VISIBLE
        val latLng = LatLng(userLocation.latitude, userLocation.longitude)
        googleMap.addMarker(
            MarkerOptions().position(latLng).title("Sherlock")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
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
        // adapter.caseList.set(case.caseNumber,case)
    }

    private fun navigateToAndMark(case: Case) {

        googleMap.clear()
        if (case.solved)
            googleMap.addMarker(
                MarkerOptions().position(case.latLng).title(case.caseTitle)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
        else
            googleMap.addMarker(
                MarkerOptions().position(case.latLng).title(case.caseTitle)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(case.latLng))

    }

    override fun closedSolvedCase(case: Case) {
        Toast.makeText(requireContext(), case.caseTitle, Toast.LENGTH_SHORT).show()
        navigateToAndMark(case)
    }


}


