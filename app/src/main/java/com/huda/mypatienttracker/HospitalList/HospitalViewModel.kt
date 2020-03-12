package com.huda.mypatienttracker.HospitalList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel

class HospitalViewModel : ViewModel() {
    private var repositoryHelper: HospitalRepository = HospitalRepository()
    private lateinit var mutableLiveData: MutableLiveData<HospitalResponseModel>

    fun getHospitals(type: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getHospitals(type, accessToken)

    }

    fun getData(): MutableLiveData<HospitalResponseModel> {
        return mutableLiveData
    }


}
