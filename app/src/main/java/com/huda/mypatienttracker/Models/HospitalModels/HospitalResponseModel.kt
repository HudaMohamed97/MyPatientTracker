package com.huda.mypatienttracker.Models.HospitalModels

import com.example.myapplication.Models.EventModels.Links
import com.example.myapplication.Models.EventModels.Meta
import com.google.gson.annotations.SerializedName


data class HospitalResponseModel (

    @SerializedName("data") val data : List<HospitalData>,
    @SerializedName("links") val links : Links,
    @SerializedName("meta") val meta : Meta
)