package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class SpeakerRequestModel(
    @SerializedName("type") val type: String = "international",
    @SerializedName("speaker_type") val speaker_type: String,
    @SerializedName("speciality") val speciality: String,
    @SerializedName("name") val name: String
)