package com.coolcats.sherlockcoolcats.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.user_input_fragment.*

class UserInputFragment : Fragment(),MapInputFragment.MapInputDelegate {


    private lateinit var  mapInputDelegate: MapInputFragment.MapInputDelegate
    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_input_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLocation_btn.setOnClickListener {
            val mapInputFragment = MapInputFragment(this)
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.input_map, mapInputFragment, "tag")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            val case_Title : String=add_edittext.text.toString()
            // var latlng : LatLng =getLatLng(latLng)
            val case = Case(case_Title,latLng,false)
        }
    }

    private lateinit var latLng: LatLng
    override fun getLatLng(latLng: LatLng)  {
        this.latLng =latLng

    }

}