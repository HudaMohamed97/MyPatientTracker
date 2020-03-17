package com.huda.mypatienttracker.patientListFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.mypatienttracker.Models.PatientResponse
import okhttp3.ResponseBody

class PatientaListViewModel : ViewModel() {
    private var repositoryHelper: PatientRepository = PatientRepository()
    private lateinit var mutableLiveData: MutableLiveData<PatientResponse>

    fun getPatients(status: String, hospital_type: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getPatients(status, hospital_type, accessToken)

    }

    fun getData(): MutableLiveData<PatientResponse> {
        return mutableLiveData
    }
}
