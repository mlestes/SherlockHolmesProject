package com.coolcats.sherlockcoolcats.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.android.gms.maps.model.LatLng


@Entity(tableName = "case_table")
data class Cases(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "case_number")
    var caseNumber: Int,
    @ColumnInfo(name = "case_title")
    var caseTitle: String?,
    @ColumnInfo(name = "latitude")
    var latitude: Double,
    @ColumnInfo(name = "longitude")
    var longitude: Double,
    @ColumnInfo(name = "case_status")
    var solved: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    @Ignore
    constructor(caseTitle: String, latLng: LatLng, solved: Boolean) : this(
        0,
        caseTitle,
        latLng.latitude,
        latLng.longitude,
        solved
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(caseNumber)
        parcel.writeString(caseTitle)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeByte(if (solved) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cases> {
        override fun createFromParcel(parcel: Parcel): Cases {
            return Cases(parcel)
        }

        override fun newArray(size: Int): Array<Cases?> {
            return arrayOfNulls(size)
        }
    }
}
