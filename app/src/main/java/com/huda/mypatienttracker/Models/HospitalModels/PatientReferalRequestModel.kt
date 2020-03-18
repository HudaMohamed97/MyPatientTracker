package com.huda.mypatienttracker.Models.HospitalModels

import com.google.gson.annotations.SerializedName

data class PatientReferalRequestModel(
    @SerializedName("name") val name: String,
    @SerializedName("city_id") val city_id: Int = 1,
    @SerializedName("country_id") val country_id: Int = 1,
    @SerializedName("hospital_id") val hospital_id: Int,
    @SerializedName("doctor_id") val doctor_id: Int,
    @SerializedName("to_hospital") val to_hospital: Int,
    @SerializedName("to_doctor") val to_doctor: Int

)
