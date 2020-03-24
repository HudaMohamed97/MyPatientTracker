package com.example.myapplication.Models

import LoginTarget
import com.google.gson.annotations.SerializedName


data class Account(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("target") val target: LoginTarget? =null,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("photo") val photo: String = "",
    @SerializedName("device_token") val device_token: String = "",
    @SerializedName("active") val active: Boolean = false

)
