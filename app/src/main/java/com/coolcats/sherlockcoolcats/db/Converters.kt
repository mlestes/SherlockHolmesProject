package com.coolcats.sherlockcoolcats.db

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class Converters {

    //Converts the doubleArray to the LatLng object
    @TypeConverter
    fun toLatLng(doubleArray: DoubleArray): LatLng{
        return LatLng(doubleArray[0], doubleArray[1])
    }

    //Converts the LatLng object to a doubleArray
    @TypeConverter
    fun fromLatLng(latLng: LatLng): DoubleArray{
        return doubleArrayOf(latLng.latitude, latLng.longitude)
    }
}