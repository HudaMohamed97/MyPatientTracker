package com.example.myapplication.NetworkLayer

import com.example.myapplication.Models.*
import com.example.myapplication.Models.EventModels.SingleEventResponse
import com.huda.mypatienttracker.Models.AddDoctorModel
import com.huda.mypatienttracker.Models.CountriesResonse
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.addHospitalRequestModel
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

    @DELETE("hospitals/{hospital}")
    fun deleteHospital(
        @Path("hospital") postId: Int, @Header("Authorization") authHeader: String
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


    /* @POST("auth/register")
     fun register(@Body registerRequestModel: RegisterRequestModel): Call<ResponseModelData>*/
}