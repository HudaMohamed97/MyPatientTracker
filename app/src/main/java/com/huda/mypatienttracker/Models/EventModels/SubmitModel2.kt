package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class SubmitModel2(
    @SerializedName("type") val type : String,
    @SerializedName("title") val title : String,
    @SerializedName("message") val message : List<String>,
    @SerializedName("errors") val errors : String
)