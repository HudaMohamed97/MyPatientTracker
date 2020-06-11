package com.example.myapplication.Models

import LoginTarget
import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.ProductTarget


data class Account(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("target") val target: LoginTarget? = null,
    @SerializedName("product_target") val product_target: ProductTarget? = null,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("photo") val photo: String = "",
    @SerializedName("device_token") val device_token: String = "",
    @SerializedName("active") val active: Boolean = false
)
