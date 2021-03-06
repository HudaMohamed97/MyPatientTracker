package com.huda.mypatienttracker.ActivityFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.example.myapplication.Models.SubmitModel2
import com.huda.mypatienttracker.AddHospitalFragment.AddHospitalRepository
import com.huda.mypatienttracker.HospitalList.HospitalRepository
import com.huda.mypatienttracker.Models.*
import com.huda.mypatienttracker.Models.HospitalModels.CitiesResponse
import java.util.HashMap

class AddActivityViewModel : ViewModel() {
    private var countryRepositoryHelper: AddHospitalRepository = AddHospitalRepository()
    private var hospitalRepository: HospitalRepository = HospitalRepository()
    private var addActivityRepository: AddActivityRepository = AddActivityRepository()
    private lateinit var counteyMutableLiveData: MutableLiveData<CountriesResonse>
    private lateinit var cityMutableLiveData: MutableLiveData<CitiesResponse>
    private lateinit var activityMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var updateActivityMutableLiveData: MutableLiveData<SubmitModel>
    private var repositoryHelper: AddActivityRepository = AddActivityRepository()
    private lateinit var mutableLiveData: MutableLiveData<ActivityModelResponse>
    private lateinit var previousActivityLiveData: MutableLiveData<ActivityModelResponse>
    private lateinit var singelMutableLiveData: MutableLiveData<SingelActivity>
    private lateinit var doctorsMutableLiveData: MutableLiveData<DoctorsResponse>
    private lateinit var deletedMutableLiveData: MutableLiveData<SubmitModel>

    fun getActivity(page: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getActivity(page, accessToken)

    }

    fun getData(): MutableLiveData<ActivityModelResponse> {
        return mutableLiveData
    }
    fun getPreviousActivity(page: Int, accessToken: String) {
        previousActivityLiveData = repositoryHelper.getPreviousActivity(page, accessToken)

    }

    fun getPrviousActivityData(): MutableLiveData<ActivityModelResponse> {
        return previousActivityLiveData
    }

    fun getSingelActivity(ActivityId: Int, accessToken: String) {
        singelMutableLiveData = repositoryHelper.getSingelActivity(ActivityId, accessToken)

    }

    fun getSingelData(): MutableLiveData<SingelActivity> {
        return singelMutableLiveData
    }

    fun getDoctors(accessToken: String) {
        doctorsMutableLiveData = hospitalRepository.getDoctors(accessToken)

    }

    fun getDoctorData(): MutableLiveData<DoctorsResponse> {
        return doctorsMutableLiveData
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
        speakers: HashMap<String, String>,
        body: AddActivityRequestModel,
        speciality: HashMap<String, String>,
        no_attendees: HashMap<String, String>
        , accessToken: String
    ) {
        activityMutableLiveData =
            addActivityRepository.addActivity(speakers, speciality, no_attendees, body, accessToken)

    }

    fun getAddActivityData(): MutableLiveData<SubmitModel> {
        return activityMutableLiveData
    }

    fun updateActivity(
        activityId: Int,
        speakers: HashMap<String, String>,
        body: AddActivityRequestModel,
        speciality: HashMap<String, String>,
        no_attendees: HashMap<String, String>
        , accessToken: String
    ) {
        updateActivityMutableLiveData =
            addActivityRepository.updateActivity(
                activityId,
                speakers,
                speciality,
                no_attendees,
                body,
                accessToken
            )

    }

    fun getupdateActivityData(): MutableLiveData<SubmitModel> {
        return updateActivityMutableLiveData
    }


}
