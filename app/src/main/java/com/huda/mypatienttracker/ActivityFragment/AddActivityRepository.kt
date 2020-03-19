package com.huda.mypatienttracker.ActivityFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.example.myapplication.Models.SubmitModel2
import com.huda.mypatienttracker.Models.ActivityModelResponse
import com.huda.mypatienttracker.Models.AddActivityRequestModel
import com.huda.mypatienttracker.NetworkLayer.Webservice
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

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
        Log.i("hhhh", speakers.toString())
        Log.i("hhhh", speciality.toString())
        Log.i("hhhh", no_attendees.toString())
        Log.i("hhhh", body.date + body.city_id + body.product)
        val activityData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addActivity(
            body.type, "hhhhh", "opsumit",
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
