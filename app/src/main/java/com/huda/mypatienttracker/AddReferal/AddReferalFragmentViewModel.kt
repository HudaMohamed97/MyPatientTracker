package com.huda.mypatienttracker.AddReferal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.AddHospitalFragment.AddHospitalRepository
import com.huda.mypatienttracker.HospitalList.HospitalRepository
import com.huda.mypatienttracker.Models.CountriesResonse
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.HospitalModels.PatientReferalRequestModel
import com.huda.mypatienttracker.Models.SingelHospitalResponse
import com.huda.mypatienttracker.patientListFragment.PatientRepository

class AddReferalFragmentViewModel : ViewModel() {
    private var countryRepositoryHelper: AddHospitalRepository = AddHospitalRepository()
    private var repositoryHelper: HospitalRepository = HospitalRepository()
    private var patientRepository: PatientRepository = PatientRepository()
    private lateinit var mutableLiveData: MutableLiveData<HospitalResponseModel>
    private lateinit var singelMutableLiveData: MutableLiveData<SingelHospitalResponse>
    private lateinit var submitMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var counteyMutableLiveData: MutableLiveData<CountriesResonse>
    private lateinit var cityMutableLiveData: MutableLiveData<CitiesResponse>

    fun submitPatient(model: PatientReferalRequestModel, accessToken: String) {
        submitMutableLiveData = patientRepository.addReferalPatient(model, accessToken)

    }

    fun getSubmitPatient(): MutableLiveData<SubmitModel> {
        return submitMutableLiveData
    }


    fun getHospitals(type: String, accessToken: String) {
        mutableLiveData = repositoryHelper.getHospitals(1,type, accessToken)

    }

    fun getData(): MutableLiveData<HospitalResponseModel> {
        return mutableLiveData
    }

    fun getSingelHospital(hospitalId: Int, accessToken: String) {
        singelMutableLiveData = repositoryHelper.getSingelHospital(hospitalId, accessToken)

    }

    fun getSingelData(): MutableLiveData<SingelHospitalResponse> {
        return singelMutableLiveData
    }

    fun getCountries(accessToken: String) {
        counteyMutableLiveData = countryRepositoryHelper.getCountries(accessToken)

    }

    fun getCountriesData(): MutableLiveData<CountriesResonse> {
        return counteyMutableLiveData
    }

    fun getCities(countryId: Int, accessToken: String) {
        cityMutableLiveData = countryRepositoryHelper.getCities(countryId, accessToken)

    }

    fun getCitiesData(): MutableLiveData<CitiesResponse> {
        return cityMutableLiveData
    }


}
