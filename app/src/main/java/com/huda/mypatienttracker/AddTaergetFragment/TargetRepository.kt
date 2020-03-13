package com.huda.mypatienttracker.AddTaergetFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.TargetRequestModel
import com.huda.mypatienttracker.Models.TargetResponse
import com.huda.mypatienttracker.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TargetRepository {
    fun addTarget(model: TargetRequestModel, accessToken: String): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addTarget(model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        if (response.code() == 422) {

                        }
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun deleteTarget(tagetId: Int, accessToken: String): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.deleteTarget(tagetId, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        if (response.code() == 422) {

                        }
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun getTarget(hospitalId: Int, accessToken: String): MutableLiveData<TargetResponse> {
        val hospitalData = MutableLiveData<TargetResponse>()
        Webservice.getInstance().api.getTarget(hospitalId, accessToken)
            .enqueue(object : Callback<TargetResponse> {
                override fun onResponse(
                    call: Call<TargetResponse>,
                    response: Response<TargetResponse>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<TargetResponse>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData
    }

}
