package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class SingelHospitalResponse (

    @SerializedName("data") val data : SingelDataHospital
)
