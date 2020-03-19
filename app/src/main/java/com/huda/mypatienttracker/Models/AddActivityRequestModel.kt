package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class AddActivityRequestModel(
    @SerializedName("type") val type: Int,
    @SerializedName("subtype") val subtype: String,
    @SerializedName("product") val product: String,
    @SerializedName("date") val date: String,
    @SerializedName("speakers") val speakers: List<SpeakerRequestModel>,
    @SerializedName("city_id") val city_id: Int
)
