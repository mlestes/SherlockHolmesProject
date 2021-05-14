package com.coolcats.sherlockcoolcats.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import java.lang.Exception

class OpenCasesAdapter(private val openCaseDelegate: OpenCaseDelegate) : RecyclerView.Adapter<OpenCasesAdapter.OpenCaseViewHolder>() {

    var openCaseList: List<Case> = mutableListOf()

    interface OpenCaseDelegate{
        fun setOpenCaseToSolved(case: Case)
    }

    fun updateList(list: List<Case>){
        this.openCaseList = list
        notifyDataSetChanged()
    }

    inner class OpenCaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenCaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.open_cases_item_layout, parent, false)
        return OpenCaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OpenCaseViewHolder, position: Int) {
        val openCase: Case = openCaseList[position]
        openCase.let{
            holder.itemView.apply {
                this.findViewById<TextView>(R.id.case_number_textview).text = it.caseNumber.toString()
                this.findViewById<TextView>(R.id.case_title_textview).text = it.caseTitle
                this.findViewById<TextView>(R.id.case_long_textview).text = it.latLong.longitude.toString()
                this.findViewById<TextView>(R.id.case_lat_textview).text = it.latLong.latitude.toString()
                this.findViewById<Button>(R.id.case_solved_button).setOnClickListener() {
                   try {
                       openCaseDelegate.setOpenCaseToSolved(openCaseList[position])
                   } catch (e: Exception)
                   {
                       Log.d("JEFF says...",e.toString())
                   }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return openCaseList.size
    }
}