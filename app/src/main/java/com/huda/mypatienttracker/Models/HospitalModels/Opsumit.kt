package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName

data class Opsumit(
    @SerializedName("count") val count: Int,
    @SerializedName("target") val target: Int
)