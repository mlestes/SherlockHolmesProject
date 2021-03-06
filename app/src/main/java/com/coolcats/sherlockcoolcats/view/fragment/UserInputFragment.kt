package com.coolcats.sherlockcoolcats.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Cases
import com.coolcats.sherlockcoolcats.model.CaseApplication
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModel
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModelFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.user_input_fragment.*

class UserInputFragment : Fragment(), MapInputFragment.MapInputDelegate {
    private val viewModel: CaseViewModel by viewModels {
        CaseViewModelFactory((requireActivity().application as CaseApplication).repository)
    }
    private var latLng: LatLng? = null
    private val mapInputFragment = MapInputFragment(this)

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_input_fragment, container, false)
    }

    //convert string to Editable
    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .replace(R.id.map_frame, mapInputFragment)
            .commit()

        add_case_btn.setOnClickListener {
            val caseTitle: String = add_edittext.text.toString()
            if (caseTitle.isNotEmpty() && latLng != null) {
                val case = Cases(caseTitle, latLng!!, false)
                myLog("ME: $case")
                //TODO: send case -> viewmodel
                viewModel.insert(case)
                clearInput()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        clearInput()
    }

    private fun clearInput() {
        add_edittext.text = "".toEditable()
        lat_textView.text = "Latitude"
        long_textView.text = "Longitude"
        mapInputFragment.clearPin()
    }

    override fun getLatLng(latLng: LatLng) {
        this.latLng = latLng
        lat_textView.text = this.latLng!!.latitude.toString()
        long_textView.text = this.latLng!!.longitude.toString()
    }

}