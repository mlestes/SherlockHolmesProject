package com.coolcats.sherlockcoolcats.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import com.coolcats.sherlockcoolcats.view.adapter.OpenCasesAdapter
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.open_cases_fragment_layout.*
import java.lang.Exception

class OpenCasesFragment : Fragment(), OpenCasesAdapter.OpenCaseDelegate {

    private val adapter = OpenCasesAdapter(this)

    override fun setOpenCaseToSolved(case: Case) {
        try {
            Toast.makeText(activity?.baseContext,case.caseNumber.toString() + ": " + case.caseTitle,Toast.LENGTH_SHORT).show()
        } catch (e: Exception) { /* do nothing */ }
        //TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.open_cases_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        open_cases_recyclerview.adapter = adapter
        val list = mutableListOf(
                Case("The mystery of the missing shoe.", 1, LatLng(-34.0, 151.0), true),
                Case("The hound of Baskerville", 2, LatLng(-34.55, 151.0), true),
                Case("The missing charger.", 3, LatLng(-34.4, 150.0), true),
                Case("Mystery at the headquarters", 4, LatLng(33.9085, -84.4782), true)
        )
        adapter.updateList(list)
    }

}