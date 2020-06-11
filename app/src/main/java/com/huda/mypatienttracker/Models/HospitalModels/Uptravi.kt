package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName

data class Uptravi(

    @SerializedName("count") val count: Int,
    @SerializedName("target") val target: Int
)