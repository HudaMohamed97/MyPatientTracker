package com.huda.mypatienttracker.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class DoctorDate(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("speciality") val speciality: String,
    @SerializedName("type") val type: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(speciality)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DoctorDate> {
        override fun createFromParcel(parcel: Parcel): DoctorDate {
            return DoctorDate(parcel)
        }

        override fun newArray(size: Int): Array<DoctorDate?> {
            return arrayOfNulls(size)
        }
    }
}
