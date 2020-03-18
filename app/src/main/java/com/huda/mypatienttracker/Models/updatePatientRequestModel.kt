package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

data class updatePatientRequestModel(
    @SerializedName("type_medication") val type_medication: String,
    @SerializedName("etiology") val etiology: String,
    @SerializedName("other_medication") val other_medication: String
)