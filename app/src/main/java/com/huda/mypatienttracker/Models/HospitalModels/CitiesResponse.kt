package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName

data class CitiesResponse (

    @SerializedName("data") val data : CityData
)