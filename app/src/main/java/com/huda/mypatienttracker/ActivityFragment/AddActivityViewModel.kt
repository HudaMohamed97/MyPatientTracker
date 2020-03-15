package com.huda.mypatienttracker.ActivityFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.mypatienttracker.Models.ActivityModelResponse

class AddActivityViewModel : ViewModel() {
    private var repositoryHelper: AddActivityRepository =
        AddActivityRepository()
    private lateinit var mutableLiveData: MutableLiveData<ActivityModelResponse>

    fun getActivity(accessToken: String) {
        mutableLiveData = repositoryHelper.getActivity(accessToken)

    }

    fun getData(): MutableLiveData<ActivityModelResponse> {
        return mutableLiveData
    }


}
