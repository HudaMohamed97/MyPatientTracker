package com.huda.mypatienttracker.HospitalList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel

class HospitalViewModel : ViewModel() {
    private var repositoryHelper: HospitalRepository = HospitalRepository()
    private lateinit var mutableLiveData: MutableLiveData<HospitalResponseModel>
    private lateinit var deleteMutableLiveData: MutableLiveData<SubmitModel>

    fun getHospitals(page:Int,type: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getHospitals(page,type, accessToken)

    }

    fun getData(): MutableLiveData<HospitalResponseModel> {
        return mutableLiveData
    }

    fun deleteHospitals(hospitalId: Int, accessToken: String) {
        deleteMutableLiveData = repositoryHelper.deleteHospitals(hospitalId, accessToken)

    }

    fun getDeleteData(): MutableLiveData<SubmitModel> {
        return deleteMutableLiveData
    }


}
