package com.huda.mypatienttracker.ActivityFragment

import androidx.lifecycle.MutableLiveData
import com.huda.mypatienttracker.Models.ActivityModelResponse
import com.huda.mypatienttracker.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivityRepository {
    fun getActivity(accessToken: String): MutableLiveData<ActivityModelResponse> {
        val activityData = MutableLiveData<ActivityModelResponse>()
        Webservice.getInstance().api.getActivity(accessToken)
            .enqueue(object : Callback<ActivityModelResponse> {
                override fun onResponse(
                    call: Call<ActivityModelResponse>,
                    response: Response<ActivityModelResponse>
                ) {
                    if (response.isSuccessful) {
                        activityData.value = response.body()
                    } else {
                        activityData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ActivityModelResponse>, t: Throwable) {
                    activityData.value = null
                }
            })
        return activityData
    }

}
