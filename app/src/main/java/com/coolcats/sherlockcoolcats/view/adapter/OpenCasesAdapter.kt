package com.coolcats.sherlockcoolcats.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.sherlockcoolcats.R
import com.coolcats.sherlockcoolcats.model.Case
import com.coolcats.sherlockcoolcats.util.myLog

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
                this.findViewById<TextView>(R.id.case_long_textview).text = "long: " + it.longitude.toString()
                this.findViewById<TextView>(R.id.case_lat_textview).text = "lat: " +  it.latitude.toString()

                myLog("Creating image of :>>" + it.latitude + "<<, >>" + it.longitude+"<<")

//                try {
//                    Glide.with(holder.itemView)
//                        .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
//                        .load("https://picsum.photos/200/200")
//                        .error("https://picsum.photos/200/200")
//                        .into(this.findViewById<ImageView>(R.id.location_clip_imageview));
//                } catch (e: Exception) {
//                    myLog(e.toString())}

                this.findViewById<Button>(R.id.case_solved_button).setOnClickListener() {v ->
                    confirmCaseSolvedsStatusChange(v.context,it)

                //                   try {
//                       openCaseDelegate.setCaseStatus(openCaseList[position],true)
//                   } catch (e: Exception)
//                   {
//                       Log.d("JEFF says...",e.toString())
//                   }
                }
            }
        }
    }

    private fun confirmCaseSolvedsStatusChange(c:Context, case: Case) {
        val builder = AlertDialog.Builder(c)
        builder.setTitle(R.string.case_status_change_dialog_title)
        builder.setMessage(c.getString(R.string.case_status_change_dialog_message, case.caseNumber, case.caseTitle))
        builder.setCancelable(true)
        builder.setNeutralButton("Cancel") { dialog,which ->
            //do nothing
        }
        builder.setPositiveButton(R.string.case_status_change_dialog_confirm_text) { dialog, which ->
            myLog("builder.setPositiveButton>$which")
            openCaseDelegate.setCaseStatus(case,true)
        }
        builder.show()
    }

    override fun getItemCount(): Int {
        return openCaseList.size
    }
}