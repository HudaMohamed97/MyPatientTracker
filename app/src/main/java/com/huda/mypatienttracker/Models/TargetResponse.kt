package com.huda.mypatienttracker.Models

import com.example.myapplication.Models.EventModels.Links
import com.example.myapplication.Models.EventModels.Meta
import com.google.gson.annotations.SerializedName

class TargetResponse
    (
    @SerializedName("data")
val data: List<TargetData>,
    @SerializedName("links")
val links: Links,
    @SerializedName("meta")
val meta: Meta
)
