package com.huda.mypatienttracker.patientListFragment

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.*
import com.huda.mypatienttracker.Models.HospitalModels.HospitalResponseModel
import com.huda.mypatienttracker.Models.HospitalModels.PatientReferalRequestModel
import com.huda.mypatienttracker.NetworkLayer.Webservice
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientRepository {

    fun getDoctors(
        accessToken: String
    ): MutableLiveData<DoctorsResponse> {
        val hospitalData = MutableLiveData<DoctorsResponse>()
        Webservice.getInstance().api.getDoctors(accessToken)
            .enqueue(object : Callback<DoctorsResponse> {
                override fun onResponse(
                    call: Call<DoctorsResponse>,
                    response: Response<DoctorsResponse>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DoctorsResponse>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }


    fun getPatients(
        page: Int,
        /*  status: String,
          hospitalType: String,*/
        accessToken: String
    ): MutableLiveData<PatientResponse> {
        val hospitalData = MutableLiveData<PatientResponse>()
        Webservice.getInstance().api.getReferalPatient(page, accessToken)
            .enqueue(object : Callback<PatientResponse> {
                override fun onResponse(
                    call: Call<PatientResponse>,
                    response: Response<PatientResponse>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PatientResponse>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun getPatientsByHospital(
        hospitalId: Int,
        accessToken: String
    ): MutableLiveData<PatientResponse> {
        val hospitalData = MutableLiveData<PatientResponse>()
        Webservice.getInstance().api.getPatientByHospital(hospitalId, accessToken)
            .enqueue(object : Callback<PatientResponse> {
                override fun onResponse(
                    call: Call<PatientResponse>,
                    response: Response<PatientResponse>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PatientResponse>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData
    }

    fun getPatientsByDoctor(
        doctorId: Int,
        accessToken: String
    ): MutableLiveData<PatientResponse> {
        val hospitalData = MutableLiveData<PatientResponse>()
        Webservice.getInstance().api.getPatientByDoctor(doctorId, accessToken)
            .enqueue(object : Callback<PatientResponse> {
                override fun onResponse(
                    call: Call<PatientResponse>,
                    response: Response<PatientResponse>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PatientResponse>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun getAllHospitals(
        accessToken: String
    ): MutableLiveData<HospitalResponseModel> {
        val hospitalData = MutableLiveData<HospitalResponseModel>()
        Webservice.getInstance().api.getAllHospital(accessToken)
            .enqueue(object : Callback<HospitalResponseModel> {
                override fun onResponse(
                    call: Call<HospitalResponseModel>,
                    response: Response<HospitalResponseModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<HospitalResponseModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun getCoePatients(
        page: Int,
        status: String,
        accessToken: String
    ): MutableLiveData<PatientResponse> {
        val hospitalData = MutableLiveData<PatientResponse>()
        Webservice.getInstance().api.getCoePatient(page, status, accessToken)
            .enqueue(object : Callback<PatientResponse> {
                override fun onResponse(
                    call: Call<PatientResponse>,
                    response: Response<PatientResponse>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PatientResponse>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun addPatient(
        model: PatientRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addPatient(model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun addReferalPatient(
        model: PatientReferalRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.addReferalPatient(model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun updateCoePatient(
        patientId: Int,
        model: updatePatientRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.updatePatient(patientId, model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun updateReferalPatient(
        patientId: Int,
        model: updateReferalPatientRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        Webservice.getInstance().api.updateReferalPatient(patientId, model, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

    fun updateStatuesPatient(
        patientId: Int,
        model: String,
        accessToken: String
    ): MutableLiveData<SubmitModel> {
        val hospitalData = MutableLiveData<SubmitModel>()
        val body = mapOf(
            "status" to model

        )
        Webservice.getInstance().api.updatePatientSatues(patientId, body, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        hospitalData.value = response.body()
                    } else {
                        hospitalData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    hospitalData.value = null
                }
            })
        return hospitalData

    }

}
