package com.huda.mypatienttracker.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Last_treatment (

	@SerializedName("id") val id : Int,
	@SerializedName("type_medication") val type_medication : String,
	@SerializedName("etiology") val etiology : String,
	@SerializedName("other_medication") val other_medication : String,
	@SerializedName("created_at") val created_at : String
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readInt(),
		parcel.readString().toString(),
		parcel.readString().toString(),
		parcel.readString().toString(),
		parcel.readString().toString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(id)
		parcel.writeString(type_medication)
		parcel.writeString(etiology)
		parcel.writeString(other_medication)
		parcel.writeString(created_at)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Last_treatment> {
		override fun createFromParcel(parcel: Parcel): Last_treatment {
			return Last_treatment(parcel)
		}

		override fun newArray(size: Int): Array<Last_treatment?> {
			return arrayOfNulls(size)
		}
	}
}