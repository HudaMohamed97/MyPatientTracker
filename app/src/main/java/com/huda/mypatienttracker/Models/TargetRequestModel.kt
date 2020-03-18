package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName

class TargetRequestModel(
    @SerializedName("number") var number: Int,
    @SerializedName("year") var year: Int,
    @SerializedName("month") var month: Int,
    @SerializedName("product") var product: String
)

