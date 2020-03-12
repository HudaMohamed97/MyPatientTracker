package com.huda.mypatienttracker.Models.HospitalModels

import City
import Country
import User
import com.google.gson.annotations.SerializedName

data class HospitalData (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("rheuma") val rheuma : Int,
    @SerializedName("crdio") val crdio : Int,
    @SerializedName("pulmo") val pulmo : Int,
    @SerializedName("pah_expert") val pah_expert : Int,
    @SerializedName("rhc") val rhc : Int,
    @SerializedName("rwe") val rwe : Int,
    @SerializedName("echo") val echo : Int,
    @SerializedName("pah_attentive") val pah_attentive : Int,
    @SerializedName("city") val city : City,
    @SerializedName("country") val country : Country,
    @SerializedName("user") val user : User
)