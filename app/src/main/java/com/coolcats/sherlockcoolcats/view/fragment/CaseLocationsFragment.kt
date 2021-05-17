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
import androidx.fragment.app.viewModels
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.CaseApplication
import com.coolcats.sherlockcoolcats.model.Cases
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.view.adapter.SolvedCaseAdapter
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModel
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_case_locations.*


class CaseLocationsFragment : Fragment(), LocationListener, SolvedCaseAdapter.SolCaseDelegate {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var adapter: SolvedCaseAdapter
    private val solveList: MutableList<Cases> = mutableListOf()
    private val caseViewModel: CaseViewModel by viewModels { CaseViewModelFactory((requireActivity().application as CaseApplication).repository) }


    private val callback = OnMapReadyCallback { googleMap ->
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

        adapter = SolvedCaseAdapter(this)
        caseViewModel.allCases.observe(viewLifecycleOwner, { caseList ->
            solveList.clear()
            caseList.forEach {
                solveList.add(it)
            }
            adapter.updateList(solveList)
        })

        case_recyclerview.adapter = adapter
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

    override fun openSolvedCase(cases: Cases) {

        Toast.makeText(requireContext(), cases.caseTitle, Toast.LENGTH_SHORT).show()
        navigateToAndMark(cases)
        // adapter.caseList.set(case.caseNumber,case)
    }

    private fun navigateToAndMark(cases: Cases) {

        googleMap.clear()
        if (cases.solved)
            googleMap.addMarker(
                MarkerOptions().position(LatLng(cases.latitude, cases.longitude))
                    .title(cases.caseTitle)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
        else
            googleMap.addMarker(
                MarkerOptions().position(LatLng(cases.latitude, cases.longitude))
                    .title(cases.caseTitle)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(cases.latitude, cases.longitude)))

    }

}


