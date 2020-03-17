package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class Doctors(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("speciality") val speciality: String,
    @SerializedName("type") val type: String
)