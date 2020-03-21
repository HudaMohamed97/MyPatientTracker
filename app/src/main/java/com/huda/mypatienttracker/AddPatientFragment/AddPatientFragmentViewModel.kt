package com.huda.mypatienttracker.AddPatientFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.HospitalList.HospitalRepository
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.PatientRequestModel
import com.huda.mypatienttracker.Models.SingelHospitalResponse
import com.huda.mypatienttracker.Models.updatePatientRequestModel
import com.huda.mypatienttracker.patientListFragment.PatientRepository

class AddPatientFragmentViewModel : ViewModel() {
    private var repositoryHelper: HospitalRepository = HospitalRepository()
    private var patientRepository: PatientRepository = PatientRepository()
    private lateinit var mutableLiveData: MutableLiveData<HospitalResponseModel>
    private lateinit var submitMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var singelMutableLiveData: MutableLiveData<SingelHospitalResponse>
    private lateinit var updateMutableLiveData: MutableLiveData<SubmitModel>

    fun getHospitals(page: Int, type: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getHospitals(page, type, accessToken)

    }

    fun getData(): MutableLiveData<HospitalResponseModel> {
        return mutableLiveData
    }

    fun submitPatient(model: PatientRequestModel, accessToken: String) {
        submitMutableLiveData = patientRepository.addPatient(model, accessToken)

    }

    fun getSubmitPatient(): MutableLiveData<SubmitModel> {
        return submitMutableLiveData
    }

    fun updatePatient(patientId: Int, model: updatePatientRequestModel, accessToken: String) {
        updateMutableLiveData = patientRepository.updateCoePatient(patientId, model, accessToken)

    }

    fun getUpdatePatient(): MutableLiveData<SubmitModel> {
        return updateMutableLiveData
    }


    fun getSingelHospital(hospitalId: Int, accessToken: String) {
        singelMutableLiveData = repositoryHelper.getSingelHospital(hospitalId, accessToken)

    }

    fun getSingelData(): MutableLiveData<SingelHospitalResponse> {
        return singelMutableLiveData
    }
}
