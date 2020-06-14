package com.huda.mypatienttracker.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData

data class PatientResponseData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") var status: String,
    @SerializedName("is_referral") val is_referral: Boolean,
    @SerializedName("doctor") val doctor: DoctorDate,
    @SerializedName("hospital") val hospital: HospitalData?,
    @SerializedName("last_treatment") val last_treatment : Last_treatment,
    @SerializedName("created_at") val created_at: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(DoctorDate::class.java.classLoader)!!,
        parcel.readParcelable(HospitalData::class.java.classLoader),
        parcel.readParcelable(Last_treatment::class.java.classLoader)!!,
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeByte(if (is_referral) 1 else 0)
        parcel.writeParcelable(doctor, flags)
        parcel.writeParcelable(hospital, flags)
        parcel.writeParcelable(last_treatment, flags)
        parcel.writeString(created_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PatientResponseData> {
        override fun createFromParcel(parcel: Parcel): PatientResponseData {
            return PatientResponseData(parcel)
        }

        override fun newArray(size: Int): Array<PatientResponseData?> {
            return arrayOfNulls(size)
        }
    }
}
