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
            updateList()
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
        list.add(Case(1, "The Adventure of the Speckled Band", 0.0, 0.0, false))
        list.add(Case(2, "The Red-Headed League", -99.99, 99.99, true))
        list.add(Case(3, "The Adventure of the Dancing Men", -1.0, 1.0, false))
        list.add(Case(4, "A Case of Identity", -1.0, 1.0, false))
        list.add(Case(5, "The Boscombe Valley Mystery", -1.0, 1.0, false))
        list.add(Case(6, "The Adventure of the Copper Beeches", -1.0, 1.0, true))
        list.add(Case(7, "The Adventure of the Stockbroker's Clerk", -1.0, 1.0, false))
        list.add(Case(8, "The Adventure of the Greek Interpreter", -1.0, 1.0, false))
        list.add(Case(9, "The Adventure of the Dying Detective", -1.0, 1.0, false))
        updateList()
    }

    private fun updateList() {
        val myNewList: MutableList<Case> = list.filter { it -> !it.solved }.toMutableList()
        adapter.updateList(myNewList)
    }
}