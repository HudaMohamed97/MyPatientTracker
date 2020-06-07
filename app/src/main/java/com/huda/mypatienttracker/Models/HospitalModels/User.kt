package com.huda.mypatienttracker.Models.HospitalModels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("type") val type: Int,
    @SerializedName("photo") val photo: String,
    @SerializedName("device_token") val device_token: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeInt(type)
        parcel.writeString(photo)
        parcel.writeString(device_token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}