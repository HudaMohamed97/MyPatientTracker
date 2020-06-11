package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData

class TargetData(
    @SerializedName("id") val id : Int,
    @SerializedName("product") val product : String,
    @SerializedName("number") val number : Int,
    @SerializedName("year") val year : Int,
    @SerializedName("month") val month : Int
    //@SerializedName("hospital") val hospital : HospitalData
)
