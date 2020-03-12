package com.huda.mypatienttracker.AddDoctorFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.AddDoctorModel

class AddFragmentViewModel:ViewModel() {
    private var repositoryHelper: AddDoctorRepository = AddDoctorRepository()
    private lateinit var mutableLiveData: MutableLiveData<SubmitModel>

    fun addDoctor(model: AddDoctorModel, accessToken: String) {
        mutableLiveData = repositoryHelper.addDoctor(model, accessToken)

    }

    fun submitData(): MutableLiveData<SubmitModel> {
        return mutableLiveData
    }

}
