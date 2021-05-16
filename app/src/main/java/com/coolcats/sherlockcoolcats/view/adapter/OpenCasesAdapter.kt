package com.coolcats.sherlockcoolcats.view.adapter

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import com.coolcats.sherlockcoolcats.util.myLog
import java.lang.Exception
import java.util.logging.Logger

class OpenCasesAdapter(private val openCaseDelegate: OpenCaseDelegate) : RecyclerView.Adapter<OpenCasesAdapter.OpenCaseViewHolder>() {

    var openCaseList: List<Case> = mutableListOf()

    interface OpenCaseDelegate{
        fun setCaseStatus(case: Case, status: Boolean)
    }

    fun updateList(list: MutableList<Case>){
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
                this.findViewById<TextView>(R.id.case_long_textview).text = it.longitude.toString()
                this.findViewById<TextView>(R.id.case_lat_textview).text = it.latitude.toString()
                this.findViewById<Switch>(R.id.case_solved_status_switch).isChecked = it.solved

                myLog("Creating image of :>>" + it.latitude + "<<, >>" + it.longitude+"<<")

//                try {
//                    Glide.with(holder.itemView)
//                        .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
//                        .load("https://picsum.photos/200/200")
//                        .error("https://picsum.photos/200/200")
//                        .into(this.findViewById<ImageView>(R.id.location_clip_imageview));
//                } catch (e: Exception) {
//                    myLog(e.toString())}

                this.findViewById<Button>(R.id.case_solved_button).setOnClickListener() {
                   try {
                       openCaseDelegate.setCaseStatus(openCaseList[position],true)
                   } catch (e: Exception)
                   {
                       Log.d("JEFF says...",e.toString())
                   }
                }
                this.findViewById<Switch>(R.id.case_solved_status_switch).setOnCheckedChangeListener {_, isChecked ->
                    try {
                        openCaseDelegate.setCaseStatus(openCaseList[position], isChecked)
                        myLog("Switch tripped")
                        //TODO:
                    } catch (e: Exception)
                    {
                        myLog(e.toString())
                    }
                }
                
            }
        }
    }

    override fun getItemCount(): Int {
        return openCaseList.size
    }
}