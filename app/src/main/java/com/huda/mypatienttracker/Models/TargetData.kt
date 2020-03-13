package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData

class TargetData(
    @SerializedName("id") val id: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("month") val month: Int,
    @SerializedName("hospital_id") val hospital_id: HospitalData
)
