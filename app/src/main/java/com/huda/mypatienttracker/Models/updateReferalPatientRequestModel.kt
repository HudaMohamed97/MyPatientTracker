package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class updateReferalPatientRequestModel(
    @SerializedName("hospital_id") val hospital_id: Int,
    @SerializedName("doctor_id") val doctor_id: Int,
    @SerializedName("to_hospital") val to_hospital: Int? = null,
    @SerializedName("to_doctor") val to_doctor: Int? = null,
    @SerializedName("type_medication") val type_medication: String? = null,
    @SerializedName("etiology") val etiology: String? = null,
    @SerializedName("other_medication") val other_medication: String? = null
)