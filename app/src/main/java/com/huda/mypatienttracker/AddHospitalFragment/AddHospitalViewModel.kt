package com.huda.mypatienttracker.AddHospitalFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.CountriesResonse
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import com.huda.mypatienttracker.Models.addHospitalRequestModel

class AddHospitalViewModel : ViewModel() {
    private var repositoryHelper: AddHospitalRepository = AddHospitalRepository()
    private lateinit var mutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var counteyMutableLiveData: MutableLiveData<CountriesResonse>
    private lateinit var cityMutableLiveData: MutableLiveData<CitiesResponse>


    fun addHospitals(model: addHospitalRequestModel, accessToken: String) {
        mutableLiveData = repositoryHelper.addHospital(model, accessToken)

    }

    fun submitData(): MutableLiveData<SubmitModel> {
        return mutableLiveData
    }


    fun getCountries(accessToken: String) {
        counteyMutableLiveData = repositoryHelper.getCountries(accessToken)

    }

    fun getCountriesData(): MutableLiveData<CountriesResonse> {
        return counteyMutableLiveData
    }

    fun getCities(countryId: Int, accessToken: String) {
        cityMutableLiveData = repositoryHelper.getCities(countryId,accessToken)

    }
    fun getCitiesData(): MutableLiveData<CitiesResponse> {
        return cityMutableLiveData
    }


}
