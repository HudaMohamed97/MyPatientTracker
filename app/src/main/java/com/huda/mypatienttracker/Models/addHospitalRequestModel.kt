package com.huda.mypatienttracker.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class addHospitalRequestModel(
    @SerializedName("name")var name: String,
    @SerializedName("type")var type: String,
    @SerializedName("city_id")var city_id: Int,
    @SerializedName("country_id")var country_id: Int,
    @SerializedName("rheuma")var rheuma: Int,
    @SerializedName("crdio")var crdio: Int,
    @SerializedName("pulmo")var pulmo: Int,
    @SerializedName("pah_expert")var pah_expert: Int,
    @SerializedName("rhc") var rhc: Int,
    @SerializedName("rwe") var rwe: Int,
    @SerializedName("echo")var echo: Int,
    @SerializedName("pah_attentive")var pah_attentive: Int

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