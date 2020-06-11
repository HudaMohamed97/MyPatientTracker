package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName

data class Tracleer(

    @SerializedName("count") val count: Int,
    @SerializedName("target") val target: Int
)