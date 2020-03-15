package com.huda.mypatienttracker.AddPatientFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.mypatienttracker.HospitalList.HospitalRepository
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel

class AddPatientFragmentViewModel : ViewModel() {
    private var repositoryHelper: HospitalRepository = HospitalRepository()
    private lateinit var mutableLiveData: MutableLiveData<HospitalResponseModel>

    fun getHospitals(type: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getHospitals(type, accessToken)

    }

    fun getData(): MutableLiveData<HospitalResponseModel> {
        return mutableLiveData
    }
}
