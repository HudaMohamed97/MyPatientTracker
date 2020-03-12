package com.huda.mypatienttracker.AddDoctorFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.AddDoctorModel
import com.huda.mypatienttracker.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDoctorRepository {
    fun addDoctor(model: AddDoctorModel, accessToken: String): MutableLiveData<SubmitModel> {

        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addDoctor(model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    when {
                        response.isSuccessful -> hospitalData.value = response.body()
                        response.code() == 422 -> {
                            val dummyResponse =
                                SubmitModel(
                                    "The selected hospital id is invalid",
                                    "error"
                                )
                            hospitalData.value = dummyResponse
                        }
                        else -> hospitalData.value = response.body()
                    }
                }


                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData


    }

}
