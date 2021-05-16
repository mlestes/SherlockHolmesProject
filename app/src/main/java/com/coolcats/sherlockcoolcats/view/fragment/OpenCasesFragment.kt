package com.coolcats.sherlockcoolcats.view.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import com.coolcats.sherlockcoolcats.model.CaseApplication
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.view.activity.MainActivity
import com.coolcats.sherlockcoolcats.view.adapter.OpenCasesAdapter
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModel
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModelFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.open_cases_fragment_layout.*
import java.lang.Exception

class OpenCasesFragment : Fragment(), OpenCasesAdapter.OpenCaseDelegate {

    private val adapter = OpenCasesAdapter(this)
    private val caseViewModel : CaseViewModel by viewModels{CaseViewModelFactory((requireActivity().application as CaseApplication).repository)}
//    private val caseViewModel: CaseViewModel by viewModels()
    private var list: MutableList<Case> = mutableListOf()

    override fun setCaseStatus(case: Case, status: Boolean) {
        try {
            case.solved = true
//            caseViewModel.update(case)
//            try{
//                    if(!caseViewModel.allCases.value.isNullOrEmpty())   adapter.updateList(caseViewModel.allCases.value!!)
//                }
//             catch(e: Exception){ }

            Toast.makeText(activity?.baseContext,case.caseNumber.toString() + ": " + case.caseTitle,Toast.LENGTH_SHORT).show()
        } catch (e: Exception){}
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
//        if(caseViewModel.allCases.value == null)
//            myLog("CaseList: null")
//        else
//            myLog("CaseList: ${caseViewModel.allCases.value!!.size.toString()}")
        myLog("Creating OpenCaseList in OpenCaseFragment")
//        caseViewModel.allCases.observe(requireActivity()) { list ->
//            list.let {
//                adapter.updateList(it)
//            }
//        }
//        list = caseViewModel.allCases.value!!
        list.add(Case(1, "Paint off", 0.0, 0.0, false))
        list.add(Case(2, "Haters gotta hate", -99.99, 99.99, true))
        adapter.updateList(list)
    }
}