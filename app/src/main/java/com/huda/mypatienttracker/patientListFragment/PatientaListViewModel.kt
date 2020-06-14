package com.huda.mypatienttracker.patientListFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.HospitalList.HospitalRepository
import com.huda.mypatienttracker.Models.DoctorsResponse
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.PatientResponse
import okhttp3.ResponseBody

class PatientaListViewModel : ViewModel() {
    private var repositoryHelper: PatientRepository = PatientRepository()
    private var patientRepository: PatientRepository = PatientRepository()
    private lateinit var mutableLiveData: MutableLiveData<PatientResponse>
    private lateinit var doctorsMutableLiveData: MutableLiveData<DoctorsResponse>
    private lateinit var liveData: MutableLiveData<PatientResponse>
    private lateinit var doctorLiveData: MutableLiveData<PatientResponse>
    private lateinit var hospitalLiveData: MutableLiveData<HospitalResponseModel>
    private lateinit var updateStatuesMutableLiveData: MutableLiveData<SubmitModel>


    fun getPatients(page: Int, /*status: String, hospital_type: String,*/ accessToken: String) {
        mutableLiveData = repositoryHelper.getPatients(page, accessToken)

    }

    fun getData(): MutableLiveData<PatientResponse> {
        return mutableLiveData
    }

    fun getDoctors(accessToken: String) {
        doctorsMutableLiveData = repositoryHelper.getDoctors(accessToken)

    }

    fun getDoctorsData(): MutableLiveData<DoctorsResponse> {
        return doctorsMutableLiveData
    }

    fun getPatientsByHospital(hospitalId: Int, accessToken: String) {
        liveData = repositoryHelper.getPatientsByHospital(hospitalId, accessToken)

    }

    fun getHospitalPatientData(): MutableLiveData<PatientResponse> {
        return liveData
    }
    fun getPatientsByDoctor(doctorId: Int, accessToken: String) {
        doctorLiveData = repositoryHelper.getPatientsByDoctor(doctorId, accessToken)

    }

    fun getdoctorPatientData(): MutableLiveData<PatientResponse> {
        return doctorLiveData
    }

    fun getHospitals(accessToken: String) {
        hospitalLiveData = repositoryHelper.getAllHospitals(accessToken)

    }

    fun getHospitalsData(): MutableLiveData<HospitalResponseModel> {
        return hospitalLiveData
    }

    fun updateStatuesPatient(patientId: Int, satues: String, accessToken: String) {
        updateStatuesMutableLiveData =
            patientRepository.updateStatuesPatient(patientId, satues, accessToken)

    }

    fun getUpdateStatuesPatient(): MutableLiveData<SubmitModel> {
        return updateStatuesMutableLiveData
    }
}
