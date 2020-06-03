package com.huda.mypatienttracker.CoePatientLIst

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.mypatienttracker.Models.PatientResponse
import com.huda.mypatienttracker.patientListFragment.PatientRepository

class CeoPatientaListViewModel : ViewModel() {
    private var repositoryHelper: PatientRepository = PatientRepository()
    private lateinit var mutableLiveData: MutableLiveData<PatientResponse>

    fun getPatients(page:Int,status: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getCoePatients(page,status, accessToken)

    }

    fun getData(): MutableLiveData<PatientResponse> {
        return mutableLiveData
    }

}
