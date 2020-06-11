package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName

data class TotalTargetData(
    @SerializedName("total") val total: Int,
    @SerializedName("uptravi") val uptravi: Uptravi,
    @SerializedName("opsumit") val opsumit: Opsumit,
    @SerializedName("tracleer") val tracleer: Tracleer
)
