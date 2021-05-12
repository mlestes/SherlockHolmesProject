package com.coolcats.sherlockcoolcats.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case

class CaseAdapter(private val solvedCaseDelegate: SolvedCaseDelegate): RecyclerView.Adapter<CaseAdapter.CaseViewHolder>() {


    var caseList: MutableList<Case> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    interface SolvedCaseDelegate{
        fun openSolvedCase(case: Case)
    }

    inner class CaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.case_item_layout, parent, false)
        return CaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.case_number_textview).text = caseList[position].caseNumber.toString()

        holder.itemView.setOnClickListener {
            solvedCaseDelegate.openSolvedCase(caseList[position])
        }
    }

    override fun getItemCount(): Int = caseList.size
}