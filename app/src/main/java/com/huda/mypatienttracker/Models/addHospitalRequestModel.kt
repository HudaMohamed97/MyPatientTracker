package com.huda.mypatienttracker.Models

import android.os.Parcel
import android.os.Parcelable

data class addHospitalRequestModel(
    var name: String,
    var type: String,
    var city_id: Int,
    var country_id: Int,
    var rheuma: Int,
    var crdio: Int,
    var pulmo: Int,
    var pah_expert: Int,
    var rhc: Int,
    var rwe: Int,
    var echo: Int,
    var pah_attentive: Int

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) 

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeInt(city_id)
        parcel.writeInt(country_id)
        parcel.writeInt(rheuma)
        parcel.writeInt(crdio)
        parcel.writeInt(pulmo)
        parcel.writeInt(pah_expert)
        parcel.writeInt(rhc)
        parcel.writeInt(rwe)
        parcel.writeInt(echo)
        parcel.writeInt(pah_attentive)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<addHospitalRequestModel> {
        override fun createFromParcel(parcel: Parcel): addHospitalRequestModel {
            return addHospitalRequestModel(parcel)
        }

        override fun newArray(size: Int): Array<addHospitalRequestModel?> {
            return arrayOfNulls(size)
        }
    }
}