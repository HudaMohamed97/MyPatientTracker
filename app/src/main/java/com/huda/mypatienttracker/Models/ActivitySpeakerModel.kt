package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class ActivitySpeakerModel(
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("speaker_type") val speaker_type: String,
    @SerializedName("speciality") val speciality: String
)
