package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName



data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("type") val type: Int,
    @SerializedName("photo") val photo: String,
    @SerializedName("device_token") val device_token: String
)