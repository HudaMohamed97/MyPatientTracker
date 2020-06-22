package com.huda.mypatienttracker.Models

import City
import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.HospitalModels.User

data class ActivityData(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: Int,
    @SerializedName("subtype") val subtype: String,
    @SerializedName("product") val product: String,
    @SerializedName("date") val date: String,
    @SerializedName("speciality") val speciality: List<String>,
    @SerializedName("no_attendees") val no_attendees: List<String>,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("city") val city: City,
    @SerializedName("country") val country: CountryData,
    @SerializedName("user") val user: User,
    @SerializedName("speakers") val speakers: List<SpeakerRequestModel>
)
