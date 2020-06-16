package com.huda.mypatienttracker.CoePatientLIst

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.mypatienttracker.Models.DoctorsResponse
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.PatientResponse
import com.huda.mypatienttracker.patientListFragment.PatientRepository

class CeoPatientaListViewModel : ViewModel() {
    private var repositoryHelper: PatientRepository = PatientRepository()
    private lateinit var mutableLiveData: MutableLiveData<PatientResponse>
    private lateinit var doctorsMutableLiveData: MutableLiveData<DoctorsResponse>
    private lateinit var liveData: MutableLiveData<PatientResponse>
    private lateinit var doctorLiveData: MutableLiveData<PatientResponse>
    private lateinit var hospitalLiveData: MutableLiveData<HospitalResponseModel>

    fun getPatients(page: Int, status: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getCoePatients(page, status, accessToken)

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
        liveData = repositoryHelper.getConfirmedPatientsByHospital(hospitalId, accessToken)

    }

    fun getHospitalPatientData(): MutableLiveData<PatientResponse> {
        return liveData
    }

    fun getPatientsByDoctor(doctorId: Int, accessToken: String) {
        doctorLiveData = repositoryHelper.getConfirmedPatientsByDoctor(doctorId, accessToken)
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


}
