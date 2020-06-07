package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData

data class PatientResponseData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") var status: String,
    @SerializedName("is_referral") val is_referral: Boolean,
    @SerializedName("doctor") val doctor: DoctorDate,
    @SerializedName("hospital") val hospital: HospitalData?,
    @SerializedName("last_treatment") val last_treatment : Last_treatment,
    @SerializedName("created_at") val created_at: String
)
