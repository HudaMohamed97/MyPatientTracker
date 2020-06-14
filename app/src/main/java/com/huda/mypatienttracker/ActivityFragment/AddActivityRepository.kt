package com.huda.mypatienttracker.ActivityFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.ActivityModelResponse
import com.huda.mypatienttracker.Models.AddActivityRequestModel
import com.huda.mypatienttracker.Models.SingelActivity
import com.huda.mypatienttracker.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddActivityRepository {
    fun getActivity(page: Int, accessToken: String): MutableLiveData<ActivityModelResponse> {
        val activityData = MutableLiveData<ActivityModelResponse>()
        Webservice.getInstance().api.getActivity(page, accessToken)
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

    fun getPreviousActivity(
        page: Int,
        accessToken: String
    ): MutableLiveData<ActivityModelResponse> {
        val activityData = MutableLiveData<ActivityModelResponse>()
        Webservice.getInstance().api.getPreviousActivity(page, accessToken)
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

    fun getSingelActivity(ActivityId: Int, accessToken: String): MutableLiveData<SingelActivity> {
        val activityData = MutableLiveData<SingelActivity>()
        Webservice.getInstance().api.getSingelActivity(ActivityId, accessToken)
            .enqueue(object : Callback<SingelActivity> {
                override fun onResponse(
                    call: Call<SingelActivity>,
                    response: Response<SingelActivity>
                ) {
                    if (response.isSuccessful) {
                        activityData.value = response.body()
                    } else {
                        activityData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SingelActivity>, t: Throwable) {
                    activityData.value = null
                }
            })
        return activityData
    }

    fun deleteActivity(activtiyId: Int, accessToken: String): MutableLiveData<SubmitModel> {
        val activityData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.deleteActivity(activtiyId, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        activityData.value = response.body()
                    } else {
                        activityData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    activityData.value = null
                }
            })
        return activityData
    }

    fun addActivity(
        speakers: HashMap<String, String>,
        speciality: HashMap<String, String>,
        no_attendees: HashMap<String, String>,
        body: AddActivityRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val activityData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addActivity(
            body.type, body.subtype, body.product,
            body.date, speciality, speakers, no_attendees, body.city_id, accessToken
        )
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        activityData.value = response.body()
                    } else {
                        activityData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    activityData.value = null
                }
            })
        return activityData
    }

    fun updateActivity(
        activityid: Int,
        speakers: HashMap<String, String>,
        speciality: HashMap<String, String>,
        no_attendees: HashMap<String, String>,
        body: AddActivityRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val activityData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.updateActivity(
            activityid,
            body.type, body.subtype, body.product,
            body.date, speciality, speakers, no_attendees, body.city_id, accessToken
        )
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        activityData.value = response.body()
                    } else {
                        activityData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    activityData.value = null
                }
            })
        return activityData
    }

}
