package com.huda.mypatienttracker.Models

import com.example.myapplication.Models.EventModels.Links
import com.example.myapplication.Models.EventModels.Meta
import com.google.gson.annotations.SerializedName

data class DoctorsResponse (

    @SerializedName("data") val data : List<DoctorDate>,
    @SerializedName("links") val links : Links,
    @SerializedName("meta") val meta : Meta
)