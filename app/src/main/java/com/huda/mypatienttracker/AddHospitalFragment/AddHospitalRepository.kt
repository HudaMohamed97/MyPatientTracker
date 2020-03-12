package com.huda.mypatienttracker.AddHospitalFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
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
}


