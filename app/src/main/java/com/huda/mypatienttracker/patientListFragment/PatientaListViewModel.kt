package com.huda.mypatienttracker.patientListFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.PatientResponse
import okhttp3.ResponseBody

class PatientaListViewModel : ViewModel() {
    private var repositoryHelper: PatientRepository = PatientRepository()
    private var patientRepository: PatientRepository = PatientRepository()
    private lateinit var mutableLiveData: MutableLiveData<PatientResponse>
    private lateinit var updateStatuesMutableLiveData: MutableLiveData<SubmitModel>


    fun getPatients(page: Int, /*status: String, hospital_type: String,*/ accessToken: String) {
        mutableLiveData = repositoryHelper.getPatients(page, accessToken)

    }

    fun getData(): MutableLiveData<PatientResponse> {
        return mutableLiveData
    }

    fun updateStatuesPatient(patientId: Int, satues: String, accessToken: String) {
        updateStatuesMutableLiveData =
            patientRepository.updateStatuesPatient(patientId, satues, accessToken)

    }

    fun getUpdateStatuesPatient(): MutableLiveData<SubmitModel> {
        return updateStatuesMutableLiveData
    }
}
