package com.huda.mypatienttracker.Models.HospitalModels

import City
import Country
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName

data class HospitalData(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("rheuma") val rheuma : Int,
    @SerializedName("crdio") val crdio : Int,
    @SerializedName("pulmo") val pulmo : Int,
    @SerializedName("pah_expert") val pah_expert : Int,
    @SerializedName("rhc") val rhc : Int,
    @SerializedName("rwe") val rwe : Int,
    @SerializedName("echo") val echo : Int,
    @SerializedName("submit_target") val submit_target : Boolean,
    @SerializedName("pah_attentive") val pah_attentive : Int,
    @SerializedName("city") val city : City,
    @SerializedName("country") val country : Country,
    @SerializedName("user") val user : User
):Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readBoolean(),
        parcel.readInt(),
        parcel.readParcelable(City::class.java.classLoader)!!,
        parcel.readParcelable(Country::class.java.classLoader)!!,
        parcel.readParcelable(User::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeInt(rheuma)
        parcel.writeInt(crdio)
        parcel.writeInt(pulmo)
        parcel.writeInt(pah_expert)
        parcel.writeInt(rhc)
        parcel.writeInt(rwe)
        parcel.writeInt(echo)
        parcel.writeInt(pah_attentive)
        parcel.writeParcelable(city, flags)
        parcel.writeParcelable(country, flags)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HospitalData> {
        override fun createFromParcel(parcel: Parcel): HospitalData {
            return HospitalData(parcel)
        }

        override fun newArray(size: Int): Array<HospitalData?> {
            return arrayOfNulls(size)
        }
    }
}

