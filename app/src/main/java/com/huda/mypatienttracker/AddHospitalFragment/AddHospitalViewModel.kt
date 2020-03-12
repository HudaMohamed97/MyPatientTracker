package com.huda.mypatienttracker.AddHospitalFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.addHospitalRequestModel

class AddHospitalViewModel : ViewModel() {
    private var repositoryHelper: AddHospitalRepository = AddHospitalRepository()
    private lateinit var mutableLiveData: MutableLiveData<SubmitModel>

    fun addHospitals(model: addHospitalRequestModel, accessToken: String) {
        mutableLiveData = repositoryHelper.addHospital(model, accessToken)

    }

    fun submitData(): MutableLiveData<SubmitModel> {
        return mutableLiveData
    }


}
