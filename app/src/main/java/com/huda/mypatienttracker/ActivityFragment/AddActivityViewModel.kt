package com.huda.mypatienttracker.ActivityFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.AddHospitalFragment.AddHospitalRepository
import com.huda.mypatienttracker.HospitalList.HospitalRepository
import com.huda.mypatienttracker.Models.ActivityModelResponse
import com.huda.mypatienttracker.Models.CountriesResonse
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.HospitalModels.PatientReferalRequestModel
import com.huda.mypatienttracker.Models.SingelHospitalResponse
import com.huda.mypatienttracker.patientListFragment.PatientRepository

class AddActivityViewModel : ViewModel() {
    private var countryRepositoryHelper: AddHospitalRepository = AddHospitalRepository()
    private lateinit var counteyMutableLiveData: MutableLiveData<CountriesResonse>
    private lateinit var cityMutableLiveData: MutableLiveData<CitiesResponse>

    private var repositoryHelper: AddActivityRepository =
        AddActivityRepository()
    private lateinit var mutableLiveData: MutableLiveData<ActivityModelResponse>

    fun getActivity(accessToken: String) {
        mutableLiveData = repositoryHelper.getActivity(accessToken)

    }

    fun getData(): MutableLiveData<ActivityModelResponse> {
        return mutableLiveData
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
