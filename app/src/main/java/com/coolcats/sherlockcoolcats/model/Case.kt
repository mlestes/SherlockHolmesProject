package com.coolcats.sherlockcoolcats.model

import androidx.room.*
import com.google.android.gms.maps.model.LatLng


open class Case(open var caseTitle: String, open var latLng: LatLng, open var solved: Boolean){
    open var caseNumber: Int = 1

    init {
        this.caseNumber++
        caseTitle = ""
        latLng = LatLng(0.0, 0.0)
        solved = false
    }
}

@Entity(tableName = "case_table")
class DataBaseCaseItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "case_name")
    override var caseNumber: Int,
    @ColumnInfo(name = "case_title")
    override var caseTitle: String,
    @ColumnInfo(name = "case_location")
    override var latLng: LatLng,

    @ColumnInfo(name = "case_status")
    override var solved: Boolean)
    : Case(caseTitle, latLng, solved){
    }

