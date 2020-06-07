package com.huda.mypatienttracker.Models

import com.google.gson.annotations.SerializedName


data class Last_treatment (

	@SerializedName("id") val id : Int,
	@SerializedName("type_medication") val type_medication : String,
	@SerializedName("etiology") val etiology : String,
	@SerializedName("other_medication") val other_medication : String,
	@SerializedName("created_at") val created_at : String
)