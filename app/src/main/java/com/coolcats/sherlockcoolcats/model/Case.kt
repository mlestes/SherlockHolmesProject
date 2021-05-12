package com.coolcats.sherlockcoolcats.model

import com.google.android.gms.maps.model.LatLng

data class Case(
    val caseTitle: String,
    val caseNumber: Int,
    val latLong: LatLng,
    val solved: Boolean
)