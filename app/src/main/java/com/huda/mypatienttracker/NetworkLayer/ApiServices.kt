package com.huda.mypatienttracker.NetworkLayer

import com.example.myapplication.Models.*
import com.huda.mypatienttracker.Models.*
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.HospitalModels.PatientReferalRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @POST("hospitals")
    fun addHospital(
        @Body body: addHospitalRequestModel, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @POST("hospitals/{hospital}/targets")
    fun addTarget(
        @Path("hospital") hospital: Int, @Body body: TargetRequestModel, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @POST("patients")
    fun addPatient(
        @Body body: PatientRequestModel, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @POST("patients/add-referal")
    fun addReferalPatient(
        @Body body: PatientReferalRequestModel, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("hospitals/{hospital}/targets")
    fun getTarget(
        @Path("hospital") hospitalId: Int, @Query("product") product: String, @Header("Authorization") authHeader: String
    ): Call<TargetResponse>

    @GET("hospitals/{hospital}/targets")
    fun getAllTarget(
        @Path("hospital") hospitalId: Int, @Header("Authorization") authHeader: String
    ): Call<TargetResponse>

    @GET("activities")
    fun getActivity(
        @Header("Authorization") authHeader: String
    ): Call<ActivityModelResponse>

    @DELETE("activities/{activity}")
    fun deleteActivity(
        @Path("activity") activity: Int, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("patients")
    fun getReferalPatient(
        @Query("status") status: String, @Query("hospital_type")
        hospital_type: String, @Header("Authorization") authHeader: String
    ): Call<PatientResponse>

    @POST("patients/{patient}/treatments")
    fun updatePatient(
        @Path("patient") patient: Int, @Body body: updatePatientRequestModel, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("patients")
    fun getCoePatient(
        @Query("hospital_type")
        hospital_type: String, @Header("Authorization") authHeader: String
    ): Call<PatientResponse>

    @DELETE("hospitals/{hospital}")
    fun deleteHospital(
        @Path("hospital") hospital: Int, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("hospitals/{hospital}")
    fun getSingelHospital(
        @Path("hospital") hospital: Int, @Header("Authorization") authHeader: String
    ): Call<SingelHospitalResponse>

    @DELETE("hospitals/{hospital}/targets/{target}")
    fun deleteTarget(
        @Path("hospital") hospital: Int, @Path("target") target: Int, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("countries/{country}")
    fun getCity(
        @Path("country") country: Int, @Header("Authorization") authHeader: String
    ): Call<CitiesResponse>

    @POST("doctors")
    fun addDoctor(
        @Body body: AddDoctorModel, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("hospitals")
    fun getHospital(
        @Query("type") type: String, @Header("Authorization") authHeader: String
    ): Call<HospitalResponseModel>

    @GET("countries")
    fun getCountries(
        @Header("Authorization") authHeader: String
    ): Call<CountriesResonse>


    @Multipart
    @POST("auth/register")
    fun register(
        @Part("email") email: RequestBody, @Part("password") password: RequestBody, @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseModelData>
}