package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName


data class ActualTarget(

    @SerializedName("total") val total: Int,
    @SerializedName("uptravi") val uptravi: Int,
    @SerializedName("opsumit") val opsumit: Int,
    @SerializedName("tracleer") val tracleer: Int
)