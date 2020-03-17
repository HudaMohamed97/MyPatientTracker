package com.huda.mypatienttracker.patientListFragment

import androidx.lifecycle.MutableLiveData
import com.huda.mypatienttracker.Models.PatientResponse
import com.huda.mypatienttracker.NetworkLayer.Webservice
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientRepository {
    fun getPatients(
        status: String,
        hospitalType: String,
        accessToken: String
    ): MutableLiveData<PatientResponse> {
        val hospitalData = MutableLiveData<PatientResponse>()
        Webservice.getInstance().api.getReferalPatient(status, hospitalType, accessToken)
            .enqueue(object : Callback<PatientResponse> {
                override fun onResponse(
                    call: Call<PatientResponse>,
                    response: Response<PatientResponse>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PatientResponse>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

}
