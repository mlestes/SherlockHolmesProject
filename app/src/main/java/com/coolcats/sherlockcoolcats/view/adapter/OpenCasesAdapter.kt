package com.coolcats.sherlockcoolcats.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Cases
import java.lang.Exception
import com.coolcats.sherlockcoolcats.util.myLog

class OpenCasesAdapter(private val openCaseDelegate: OpenCaseDelegate) : RecyclerView.Adapter<OpenCasesAdapter.OpenCaseViewHolder>() {

    var openCasesList: List<Cases> = mutableListOf()

    interface OpenCaseDelegate{
        fun setCaseStatus(cases: Cases, status: Boolean)
    }

    fun updateList(list: MutableList<Cases>){
        this.openCasesList = list
        notifyDataSetChanged()
    }

    inner class OpenCaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenCaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.open_cases_item_layout, parent, false)
        return OpenCaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OpenCaseViewHolder, position: Int) {
        val openCases: Cases = openCasesList[position]
        openCases.let{
            holder.itemView.apply {
                this.findViewById<TextView>(R.id.case_number_textview).text = it.caseNumber.toString()
                this.findViewById<TextView>(R.id.case_title_textview).text = it.caseTitle

                this.findViewById<TextView>(R.id.case_long_textview).text = "long: " + it.longitude.toString()
                this.findViewById<TextView>(R.id.case_lat_textview).text = "lat: " +  it.latitude.toString()

                myLog("Creating image of :>>" + it.latitude + "<<, >>" + it.longitude+"<<")

                this.findViewById<Button>(R.id.case_solved_button).setOnClickListener() {v ->
                    confirmCaseSolvedsStatusChange(v.context,it)

                }
            }
        }
    }

    private fun confirmCaseSolvedsStatusChange(c:Context, cases: Cases) {
        val builder = AlertDialog.Builder(c)
        builder.setTitle(R.string.case_status_change_dialog_title)
        builder.setMessage(c.getString(R.string.case_status_change_dialog_message, cases.caseNumber, cases.caseTitle))
        builder.setCancelable(true)
        builder.setNeutralButton("Cancel") { dialog,which ->
            //do nothing
        }
        builder.setPositiveButton(R.string.case_status_change_dialog_confirm_text) { dialog, which ->
            myLog("builder.setPositiveButton>$which")
            openCaseDelegate.setCaseStatus(cases,true)
        }
        builder.show()
    }

    override fun getItemCount(): Int {
        return openCasesList.size
    }
}