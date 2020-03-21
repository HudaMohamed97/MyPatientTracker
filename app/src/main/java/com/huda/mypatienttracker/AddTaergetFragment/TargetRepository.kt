package com.huda.mypatienttracker.AddTaergetFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.TargetRequestModel
import com.huda.mypatienttracker.Models.TargetResponse
import com.huda.mypatienttracker.Models.updateTargetRequestModel
import com.huda.mypatienttracker.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TargetRepository {
    fun addTarget(
        hospitalId: Int,
        model: TargetRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addTarget(hospitalId, model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        if (response.code() == 400) {
                            val dummyResponse =
                                SubmitModel(
                                    "Error, You insert Target of this Month Before!",
                                    "error"
                                )
                            hospitalData.value = dummyResponse
                        } else {
                            hospitalData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun deleteTarget(
        hospitalId: Int,
        tagetId: Int,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.deleteTarget(hospitalId, tagetId, accessToken)
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

    fun updateTarget(
        updateTarget: updateTargetRequestModel,
        hospitalId: Int,
        tagetId: Int,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.updateTarget(hospitalId, tagetId, updateTarget, accessToken)
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

    fun getTarget(
        type: String,
        hospitalId: Int,
        accessToken: String
    ): MutableLiveData<TargetResponse> {
        val hospitalData = MutableLiveData<TargetResponse>()
        Webservice.getInstance().api.getTarget(hospitalId, type, accessToken)
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

    fun getAllTarget(
        page: Int,
        hospitalId: Int,
        accessToken: String
    ): MutableLiveData<TargetResponse> {
        val hospitalData = MutableLiveData<TargetResponse>()
        Webservice.getInstance().api.getAllTarget(hospitalId, page, accessToken)
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
