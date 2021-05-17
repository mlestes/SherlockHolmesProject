package com.coolcats.sherlockcoolcats.view.fragment

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
import com.coolcats.sherlockcoolcats.view.adapter.OpenCasesAdapter
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModel
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModelFactory
import kotlinx.android.synthetic.main.open_cases_fragment_layout.*

class OpenCasesFragment : Fragment(), OpenCasesAdapter.OpenCaseDelegate {

    private val adapter = OpenCasesAdapter(this)
    private val caseViewModel: CaseViewModel by viewModels { CaseViewModelFactory((requireActivity().application as CaseApplication).repository) }

    //    private val caseViewModel: CaseViewModel by viewModels()
    private var list: MutableList<Cases> = mutableListOf()

    override fun setCaseStatus(case: Cases, status: Boolean) {
        try {
            case.solved = true
            //caseViewModel.update(case)
            Toast.makeText(
                activity?.baseContext,
                case.caseNumber.toString() + ": " + case.caseTitle + " updated",
                Toast.LENGTH_SHORT
            ).show()
            updateList()
        } catch (e: Exception) {
        }
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
        //
//
//        list.add(Cases(1, "The Adventure of the Speckled Band", 0.0, 0.0, false))
//        list.add(Cases(2, "The Red-Headed League", -99.99, 99.99, true))
//        list.add(Cases(3, "The Adventure of the Dancing Men", -1.0, 1.0, false))
//        list.add(Cases(4, "A Case of Identity", -1.5, 3.0, false))
//        list.add(Cases(5, "The Boscombe Valley Mystery", -4.0, -5.0, false))
//        list.add(Cases(6, "The Adventure of the Copper Beeches", -7.0, 8.0, true))
//        list.add(Cases(7, "The Adventure of the Stockbroker's Clerk", 5.0, 5.0, false))
//        list.add(Cases(8, "The Adventure of the Greek Interpreter", -7.0, -4.67, false))
//        list.add(Cases(9, "The Adventure of the Dying Detective", 8.78230, 7.1233, false))
        updateList()
    }

    private fun updateList() {
//        adapter.updateList(list.filter { it -> !it.solved }.toMutableList())
        caseViewModel.allCases.value?.filter { it -> !it.solved }
            ?.let { it1 -> adapter.updateList(it1.toMutableList()) } ?: {
            adapter.updateList(mutableListOf())
        }
    }
}
