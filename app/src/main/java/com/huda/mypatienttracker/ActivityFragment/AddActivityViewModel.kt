package com.huda.mypatienttracker.ActivityFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.example.myapplication.Models.SubmitModel2
import com.huda.mypatienttracker.AddHospitalFragment.AddHospitalRepository
import com.huda.mypatienttracker.HospitalList.HospitalRepository
import com.huda.mypatienttracker.Models.ActivityModelResponse
import com.huda.mypatienttracker.Models.AddActivityRequestModel
import com.huda.mypatienttracker.Models.CountriesResonse
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.HospitalModels.PatientReferalRequestModel
import com.huda.mypatienttracker.Models.SingelHospitalResponse
import com.huda.mypatienttracker.patientListFragment.PatientRepository
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.util.HashMap

class AddActivityViewModel : ViewModel() {
    private var countryRepositoryHelper: AddHospitalRepository = AddHospitalRepository()
    private var addActivityRepository: AddActivityRepository = AddActivityRepository()
    private lateinit var counteyMutableLiveData: MutableLiveData<CountriesResonse>
    private lateinit var cityMutableLiveData: MutableLiveData<CitiesResponse>
    private lateinit var activityMutableLiveData: MutableLiveData<SubmitModel>
    private var repositoryHelper: AddActivityRepository = AddActivityRepository()
    private lateinit var mutableLiveData: MutableLiveData<ActivityModelResponse>
    private lateinit var deletedMutableLiveData: MutableLiveData<SubmitModel>

    fun getActivity(accessToken: String) {
        mutableLiveData = repositoryHelper.getActivity(accessToken)

    }

    fun getData(): MutableLiveData<ActivityModelResponse> {
        return mutableLiveData
    }

    fun deleteActivity(activtiyId: Int, accessToken: String) {
        deletedMutableLiveData = repositoryHelper.deleteActivity(activtiyId, accessToken)

    }

    fun getAactivityDeletedData(): MutableLiveData<SubmitModel> {
        return deletedMutableLiveData
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

    fun addActivity(
        speakers: HashMap<String, RequestBody>,
        body: AddActivityRequestModel,
        speciality: HashMap<String, RequestBody>,
        no_attendees: HashMap<String, RequestBody>
        , accessToken: String
    ) {
        activityMutableLiveData =
            addActivityRepository.addActivity(speakers, speciality, no_attendees, body, accessToken)

    }

    fun getAddActivityData(): MutableLiveData<SubmitModel> {
        return activityMutableLiveData
    }


}
