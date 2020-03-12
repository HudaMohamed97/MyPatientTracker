package com.huda.mypatienttracker.HospitalList

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalRepository {
    fun getHospitals(type: String, accessToken: String): MutableLiveData<HospitalResponseModel> {
        val hospitalData = MutableLiveData<HospitalResponseModel>()
        Webservice.getInstance().api.getHospital(type, accessToken)
            .enqueue(object : Callback<HospitalResponseModel> {
                override fun onResponse(
                    call: Call<HospitalResponseModel>,
                    response: Response<HospitalResponseModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<HospitalResponseModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun deleteHospitals(hospitalId: Int, accessToken: String): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.deleteHospital(hospitalId, accessToken)
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

}