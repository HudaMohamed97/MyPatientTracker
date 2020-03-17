package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class PatientRequestModel(
    @SerializedName("name") val name: String,
    @SerializedName("city_id") val city_id: Int = 1,
    @SerializedName("country_id") val country_id: Int = 1,
    @SerializedName("hospital_id") val hospital_id: Int,
    @SerializedName("doctor_id") val doctor_id: Int,
    @SerializedName("type_medication") val type_medication: String,
    @SerializedName("etiology") val etiology: String,
    @SerializedName("other_medication") val other_medication: String

)
