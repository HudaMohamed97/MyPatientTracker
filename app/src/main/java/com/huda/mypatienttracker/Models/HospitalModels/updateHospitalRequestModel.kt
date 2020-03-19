package com.huda.mypatienttracker.Models.HospitalModels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class updateHospitalRequestModel(
    @SerializedName("name") var name: String,
    @SerializedName("type") var type: String,
    @SerializedName("city_id") var city_id: Int,
    @SerializedName("country_id") var country_id: Int,
    @SerializedName("rheuma") var rheuma: Int,
    @SerializedName("crdio") var crdio: Int,
    @SerializedName("pulmo") var pulmo: Int,
    @SerializedName("pah_expert") var pah_expert: Int,
    @SerializedName("rhc") var rhc: Int,
    @SerializedName("rwe") var rwe: Int,
    @SerializedName("echo") var echo: Int,
    @SerializedName("pah_attentive") var pah_attentive: Int,
    @SerializedName("_method:put") var put: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString()
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
        parcel.writeString(put)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<updateHospitalRequestModel> {
        override fun createFromParcel(parcel: Parcel): updateHospitalRequestModel {
            return updateHospitalRequestModel(parcel)
        }

        override fun newArray(size: Int): Array<updateHospitalRequestModel?> {
            return arrayOfNulls(size)
        }
    }
}