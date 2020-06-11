package com.huda.mypatienttracker.AddTaergetFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.HospitalModels.TotalTargetResponse
import com.huda.mypatienttracker.Models.TargetRequestModel
import com.huda.mypatienttracker.Models.TargetResponse
import com.huda.mypatienttracker.Models.updateTargetRequestModel

class AddTargetFragmentViewModel : ViewModel() {
    private var repositoryHelper: TargetRepository = TargetRepository()
    private lateinit var targetMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var submitTargetLiveData: MutableLiveData<SubmitModel>
    private lateinit var updateMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var deletetMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var mutableLiveData: MutableLiveData<TargetResponse>
    private lateinit var totalTargetLiveData: MutableLiveData<TotalTargetResponse>
    private lateinit var allMutableLiveData: MutableLiveData<TargetResponse>


    fun addTarget(hospitalId: Int, model: TargetRequestModel, accessToken: String) {
        targetMutableLiveData = repositoryHelper.addTarget(hospitalId, model, accessToken)

    }

    fun submitData(): MutableLiveData<SubmitModel> {
        return targetMutableLiveData
    }

    fun updateTarget(
        hospitalId: Int,
        targetlId: Int,
        model: updateTargetRequestModel,
        accessToken: String
    ) {
        updateMutableLiveData =
            repositoryHelper.updateTarget(model, hospitalId, targetlId, accessToken)

    }

    fun updateData(): MutableLiveData<SubmitModel> {
        return updateMutableLiveData
    }

    fun deleteTarget(hospitalId: Int, tagetId: Int, accessToken: String) {
        targetMutableLiveData = repositoryHelper.deleteTarget(hospitalId, tagetId, accessToken)

    }

    fun getsubmitData(): MutableLiveData<SubmitModel> {
        return submitTargetLiveData
    }

    fun submitTarget(hospitalId: Int, accessToken: String) {
        submitTargetLiveData = repositoryHelper.submitTarget(hospitalId, accessToken)

    }

    fun submitDeleteData(): MutableLiveData<SubmitModel> {
        return targetMutableLiveData
    }

    fun getTargetData(): MutableLiveData<TargetResponse> {
        return mutableLiveData
    }

    fun getTargetTotalData(): MutableLiveData<TotalTargetResponse> {
        return totalTargetLiveData
    }

    fun getTarget(type: String, hospitalId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getTarget(type, hospitalId, accessToken)

    }

    fun getAllTarget(page: Int, hospitalId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getAllTarget(page, hospitalId, accessToken)

    }


    fun getTotalTarget(hospitalId: Int, accessToken: String) {
        totalTargetLiveData = repositoryHelper.getTotalTarget(hospitalId, accessToken)

    }

}
