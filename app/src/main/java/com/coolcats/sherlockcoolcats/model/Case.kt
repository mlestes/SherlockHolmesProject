package com.coolcats.sherlockcoolcats.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "case_table")
data class Case(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "case_number")
    var caseNumber: Int = 1,

    @ColumnInfo(name = "case_title")
    var caseTitle: String = "",

    @ColumnInfo(name ="case_location")
    var latLong: LatLng,

    @ColumnInfo(name = "case_status")
    var solved: Boolean = false
)
