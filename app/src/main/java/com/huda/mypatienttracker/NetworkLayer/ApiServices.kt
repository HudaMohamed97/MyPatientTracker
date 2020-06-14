package com.huda.mypatienttracker.NetworkLayer

import com.example.myapplication.Models.*
import com.huda.mypatienttracker.Models.*
import com.huda.mypatienttracker.Models.HospitalModels.*
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

    @PUT("hospitals/{hospital}")
    fun updateHospital(
        @Path("hospital") hospital: Int,
        @Body body: updateHospitalRequestModel,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @POST("hospitals/{hospital}/targets")
    fun addTarget(
        @Path("hospital") hospital: Int,
        @Body body: TargetRequestModel,
        @Header("Authorization") authHeader: String
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
        @Path("hospital") hospitalId: Int,
        @Query("product") product: String,
        @Header("Authorization") authHeader: String
    ): Call<TargetResponse>

    @GET("hospitals/{hospital}/total-target")
    fun getTotalTarget(
        @Path("hospital") hospitalId: Int,
        @Header("Authorization") authHeader: String
    ): Call<TotalTargetResponse>

    @POST("hospitals/{hospital}/submit-target")
    fun sumbmitTarget(
        @Path("hospital") hospitalId: Int,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("hospitals/{hospital}/targets")
    fun getAllTarget(
        @Path("hospital") hospitalId: Int,
        @Query("page") page: Int,
        @Header("Authorization") authHeader: String
    ): Call<TargetResponse>

    @GET("activities")
    fun getActivity(
        @Query("page") page: Int,
        @Header("Authorization") authHeader: String
    ): Call<ActivityModelResponse>

    @GET("previous-activities")
    fun getPreviousActivity(
        @Query("page") page: Int,
        @Header("Authorization") authHeader: String
    ): Call<ActivityModelResponse>

    @GET("activities/{activity}")
    fun getSingelActivity(
        @Path("activity") activity: Int,
        @Header("Authorization") authHeader: String
    ): Call<SingelActivity>

    @POST("activities")
    @FormUrlEncoded
    fun addActivity(
        @Field("type") type: Int,
        @Field("subtype") subtype: String,
        @Field("product") product: String,
        @Field("date") date: String,
        @FieldMap speciality: HashMap<String, String>,
        @FieldMap speakers: HashMap<String, String>,
        @FieldMap no_attendees: HashMap<String, String>,
        @Field("city_id") city_id: Int,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @PUT("activities/{activity}")
    @FormUrlEncoded
    fun updateActivity(
        @Path("activity") activity: Int,
        @Field("type") type: Int,
        @Field("subtype") subtype: String,
        @Field("product") product: String,
        @Field("date") date: String,
        @FieldMap speciality: HashMap<String, String>,
        @FieldMap speakers: HashMap<String, String>,
        @FieldMap no_attendees: HashMap<String, String>,
        @Field("city_id") city_id: Int,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @DELETE("activities/{activity}")
    fun deleteActivity(
        @Path("activity") activity: Int, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("patients")
    fun getReferalPatient(
        @Query("page") page: Int, @Header("Authorization") authHeader: String
    ): Call<PatientResponse>

    @GET("patients")
    fun getPatientByHospital(
        @Query("hospital_id") hospitalId: Int,
        @Header("Authorization") authHeader: String
    ): Call<PatientResponse>

    @GET("patients")
    fun getPatientByDoctor(
        @Query("doctor_id") doctorId: Int,
        @Header("Authorization") authHeader: String
    ): Call<PatientResponse>

    @POST("patients/{patient}/treatments")
    fun updatePatient(
        @Path("patient") patient: Int,
        @Body body: updatePatientRequestModel,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @POST("patients/{patient}/update-referal")
    fun updateReferalPatient(
        @Path("patient") patient: Int,
        @Body body: updateReferalPatientRequestModel,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @POST("patients/{patient}/update-status")
    fun updatePatientSatues(
        @Path("patient") patient: Int,
        @Body status: Map<String, String>,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("patients")
    fun getCoePatient(
        @Query("page") page: Int,
        @Query("status") status: String, @Header("Authorization") authHeader: String
    ): Call<PatientResponse>

    @PUT("hospitals/{hospital}/targets/{target}")
    fun updateTarget(
        @Path("hospital") hospital: Int,
        @Path("target") target: Int,
        @Body targetRequestModel: updateTargetRequestModel,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @DELETE("hospitals/{hospital}")
    fun deleteHospital(
        @Path("hospital") hospital: Int, @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @GET("hospitals/{hospital}")
    fun getSingelHospital(
        @Path("hospital") hospital: Int, @Header("Authorization") authHeader: String
    ): Call<SingelHospitalResponse>

    @GET("doctors")
    fun getDoctors(
        @Header("Authorization") authHeader: String
    ): Call<DoctorsResponse>

    @DELETE("hospitals/{hospital}/targets/{target}")
    fun deleteTarget(
        @Path("hospital") hospital: Int,
        @Path("target") target: Int,
        @Header("Authorization") authHeader: String
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
        @Query("type") type: String,
        @Query("page") page: Int,
        @Header("Authorization") authHeader: String
    ): Call<HospitalResponseModel>

    @GET("hospitals")
    fun getAllHospital(
        @Header("Authorization") authHeader: String
    ): Call<HospitalResponseModel>

    @GET("countries")
    fun getCountries(
        @Header("Authorization") authHeader: String
    ): Call<CountriesResonse>


    @Multipart
    @POST("auth/register")
    fun register(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseModelData>
}