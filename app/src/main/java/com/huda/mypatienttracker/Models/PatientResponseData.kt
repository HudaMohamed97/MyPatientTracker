package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData

data class PatientResponseData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("hospital") val hospital: HospitalData?,
    @SerializedName("created_at") val created_at: String
)
