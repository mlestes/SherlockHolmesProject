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
import com.coolcats.sherlockcoolcats.util.myLog
import com.coolcats.sherlockcoolcats.view.adapter.OpenCasesAdapter
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModel
import com.coolcats.sherlockcoolcats.viewmodel.CaseViewModelFactory
import kotlinx.android.synthetic.main.open_cases_fragment_layout.*

class OpenCasesFragment : Fragment(), OpenCasesAdapter.OpenCaseDelegate {

    private val adapter = OpenCasesAdapter(this)
    private val caseViewModel: CaseViewModel by viewModels { CaseViewModelFactory((requireActivity().application as CaseApplication).repository) }
    private var list: MutableList<Cases> = mutableListOf()

    override fun setCaseStatus(cases: Cases, status: Boolean) {
        try {
            cases.solved = true
            caseViewModel.update(cases)
            Toast.makeText(
                activity?.baseContext,
                cases.caseNumber.toString() + ": " + cases.caseTitle + " updated",
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
        caseViewModel.allCases.observe(viewLifecycleOwner, { caseList ->
            list.clear()
            caseList.forEach {
                list.add(it)
            }
            updateList()
        })

        updateList()
    }

    private fun updateList() {
//        adapter.updateList(list.filter { it -> !it.solved }.toMutableList())
        caseViewModel.allCases.value?.filter { it ->
            !it.solved }
            ?.let { it1 ->
                myLog("Open List Size: ${it1.size}")
                adapter.updateList(it1.toMutableList()) } ?: {
            adapter.updateList(mutableListOf())
        }
        myLog("All Cases Size: ${caseViewModel.allCases.value?.size}")
    }
}
