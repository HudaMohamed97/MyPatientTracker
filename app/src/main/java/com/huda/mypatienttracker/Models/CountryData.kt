package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)