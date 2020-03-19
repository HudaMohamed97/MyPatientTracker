package com.huda.mypatienttracker.Models
import com.google.gson.annotations.SerializedName

data class updateTargetRequestModel(
    @SerializedName("product") val product: String?,
    @SerializedName("number") var number: Int?,
    @SerializedName("year") val year: Int?,
    @SerializedName("month") val month: Int?,
    @SerializedName("_method:put") val method: String?
)

