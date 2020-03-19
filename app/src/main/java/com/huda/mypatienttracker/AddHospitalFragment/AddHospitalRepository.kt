package com.huda.mypatienttracker.AddHospitalFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.CountriesResonse
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import com.huda.mypatienttracker.Models.HospitalModels.updateHospitalRequestModel
import com.huda.mypatienttracker.Models.addHospitalRequestModel
import com.huda.mypatienttracker.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddHospitalRepository {
    fun addHospital(
        model: addHospitalRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addHospital(model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun updateHospital(
        hospitalId: Int,
        model: updateHospitalRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.updateHospital(hospitalId, model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun getCountries(
        accessToken: String
    ): MutableLiveData<CountriesResonse> {
        val countriesData = MutableLiveData<CountriesResonse>()
        Webservice.getInstance().api.getCountries(accessToken)
            .enqueue(object : Callback<CountriesResonse> {
                override fun onResponse(
                    call: Call<CountriesResonse>,
                    response: Response<CountriesResonse>
                ) {
                    if (response.isSuccessful) {
                        countriesData.value = response.body()
                    } else {
                        countriesData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<CountriesResonse>, t: Throwable) {
                    countriesData.value = null
                }
            })
        return countriesData

    }

    fun getCities(countryId: Int, accessToken: String): MutableLiveData<CitiesResponse> {
        val citiesData = MutableLiveData<CitiesResponse>()
        Webservice.getInstance().api.getCity(countryId, accessToken)
            .enqueue(object : Callback<CitiesResponse> {
                override fun onResponse(
                    call: Call<CitiesResponse>,
                    response: Response<CitiesResponse>
                ) {
                    if (response.isSuccessful) {
                        citiesData.value = response.body()
                    } else {
                        citiesData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {
                    citiesData.value = null
                }
            })
        return citiesData

    }
}


