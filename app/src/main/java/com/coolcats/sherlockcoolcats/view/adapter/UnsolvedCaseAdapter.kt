package com.coolcats.sherlockcoolcats.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import kotlinx.android.synthetic.main.open_cases_fragment_layout.*
import kotlinx.android.synthetic.main.unsolved_case_item_layout.view.*

class UnsolvedCaseAdapter(private var caseList: List<Case>):
    RecyclerView.Adapter<UnsolvedCaseAdapter.ClosedViewHolder>() {



    interface UnSolvedCaseDelegate{
        fun closedSolvedCase(case: Case)
    }


    // any error delete this class
    fun updateList(list: MutableList<Case>){
        this.caseList = list
        notifyDataSetChanged()
    }


    inner class  ClosedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClosedViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.unsolved_case_item_layout,parent,false)
        return ClosedViewHolder(itemView)
    }

    override fun getItemCount(): Int= caseList.size

    override fun onBindViewHolder(holder: ClosedViewHolder, position: Int) {
        var item =caseList[position]
        if(caseList[position].solved == false)
           // holder.itemView.unsolved_case_number_textview.text= caseList[position].caseNumber.toString()
            holder.itemView.unsolved_case_number_textview.text= item.caseNumber.toString()


    }
}