package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName

data class TotalTargetResponse(
    @SerializedName("target") val target: TotalTargetData
)
