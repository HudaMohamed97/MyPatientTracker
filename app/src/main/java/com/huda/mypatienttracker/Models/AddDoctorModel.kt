package com.huda.mypatienttracker.Models

data class AddDoctorModel(
    var name: String,
    var speciality: String,
    var type: String,
    var hospital_id: Int
)