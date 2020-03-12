package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.Cities

data class CityData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cities") val cities: List<Cities>
)
