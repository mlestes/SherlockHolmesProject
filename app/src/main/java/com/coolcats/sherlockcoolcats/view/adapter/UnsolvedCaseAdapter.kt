package com.coolcats.sherlockcoolcats.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Cases
import kotlinx.android.synthetic.main.unsolved_case_item_layout.view.*

class UnsolvedCaseAdapter(private var casesList: List<Cases>):
    RecyclerView.Adapter<UnsolvedCaseAdapter.ClosedViewHolder>() {



    interface UnSolvedCaseDelegate{
        fun closedSolvedCase(cases: Cases)
    }


    // any error delete this class
    fun updateList(list: MutableList<Cases>){
        this.casesList = list
        notifyDataSetChanged()
    }


    inner class  ClosedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClosedViewHolder {
        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.unsolved_case_item_layout,parent,false)
        return ClosedViewHolder(itemView)
    }

    override fun getItemCount(): Int= casesList.size

    override fun onBindViewHolder(holder: ClosedViewHolder, position: Int) {
        var item =casesList[position]
        if(casesList[position].solved == false)
           // holder.itemView.unsolved_case_number_textview.text= caseList[position].caseNumber.toString()
            holder.itemView.unsolved_case_number_textview.text= item.caseNumber.toString()


    }
}