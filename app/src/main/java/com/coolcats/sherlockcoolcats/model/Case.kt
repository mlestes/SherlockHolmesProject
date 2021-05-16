package com.coolcats.sherlockcoolcats.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

//
//open class Case(open var caseTitle: String, open var latLng: LatLng, open var solved: Boolean){
//    open var caseNumber: Int = 1
//
//    init {
//        this.caseNumber++
//        caseTitle = ""
//        latLng = LatLng(0.0, 0.0)
//        solved = false
//    }
//}

@Entity(tableName = "case_table")
data class Case(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "case_name") val caseNumber: Int,
    @ColumnInfo(name = "case_title") var caseTitle: String,
    @ColumnInfo(name = "case_location") var latLng: LatLng,
    @ColumnInfo(name = "case_status") var solved: Boolean
) {

    @Ignore
    constructor(caseTitle: String, latLng: LatLng, solved: Boolean) : this(
        0,
        caseTitle,
        latLng,
        solved
    )
}


