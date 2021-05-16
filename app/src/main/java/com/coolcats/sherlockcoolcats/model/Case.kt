package com.coolcats.sherlockcoolcats.model

import androidx.room.*
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "case_table")
open class Case(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "case_number")
    var caseNumber: Int,
    @ColumnInfo(name = "case_title")
    var caseTitle : String,
    @ColumnInfo(name = "case_location")
    var latLng: LatLng,
    @ColumnInfo(name = "case_status")
    var solved: Boolean ){

    @Ignore
    constructor(caseTitle: String, latLng: LatLng, solved: Boolean) : this(
        0,
        caseTitle,
        latLng,
        solved
    )
}
