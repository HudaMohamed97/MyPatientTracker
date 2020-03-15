package com.huda.mypatienttracker.patientListFragment

import androidx.lifecycle.MutableLiveData
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
    ): MutableLiveData<ResponseBody> {
        val hospitalData = MutableLiveData<ResponseBody>()
        Webservice.getInstance().api.getReferalPatient(status, hospitalType, accessToken)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

}
