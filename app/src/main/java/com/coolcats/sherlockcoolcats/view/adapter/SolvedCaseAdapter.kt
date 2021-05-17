package com.coolcats.sherlockcoolcats.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Cases
import kotlinx.android.synthetic.main.case_item_layout.view.*

class SolvedCaseAdapter(private val solCaseDelegate: SolCaseDelegate) :
    RecyclerView.Adapter<SolvedCaseAdapter.SolvedViewHolder>() {

    private var casesList: MutableList<Cases> = mutableListOf()

    interface SolCaseDelegate {
        fun openSolvedCase(cases: Cases)
    }

    fun updateList(list: MutableList<Cases>) {
        this.casesList = list
        notifyDataSetChanged()
    }

    inner class SolvedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvedViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.case_item_layout, parent, false)
        return SolvedViewHolder(itemView)
    }

    override fun getItemCount(): Int = casesList.size

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: SolvedViewHolder, position: Int) {
        var item = casesList[position]
        if (item.solved == true) {
            holder.itemView.case_number_textview.text = item.caseNumber.toString()
                .also { holder.itemView.case_number_textview.setBackgroundResource(R.drawable.ic_solved) }

        } else
            holder.itemView.case_number_textview.text = item.caseNumber.toString()
                .also { holder.itemView.case_number_textview.setBackgroundResource(R.drawable.ic_unsolved) }
        holder.itemView.setOnClickListener {
            solCaseDelegate.openSolvedCase(item)
        }

    }

}