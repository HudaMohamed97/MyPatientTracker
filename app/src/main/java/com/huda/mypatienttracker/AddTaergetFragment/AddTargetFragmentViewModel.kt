package com.huda.mypatienttracker.AddTaergetFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.TargetRequestModel
import com.huda.mypatienttracker.Models.TargetResponse

class AddTargetFragmentViewModel : ViewModel() {
    private var repositoryHelper: TargetRepository = TargetRepository()
    private lateinit var targetMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var deletetMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var mutableLiveData: MutableLiveData<TargetResponse>
    private lateinit var allMutableLiveData: MutableLiveData<TargetResponse>


    fun addTarget(hospitalId: Int, model: TargetRequestModel, accessToken: String) {
        targetMutableLiveData = repositoryHelper.addTarget(hospitalId, model, accessToken)

    }

    fun submitData(): MutableLiveData<SubmitModel> {
        return targetMutableLiveData
    }

    fun deleteTarget(hospitalId:Int,tagetId: Int, accessToken: String) {
        targetMutableLiveData = repositoryHelper.deleteTarget(hospitalId,tagetId, accessToken)

    }

    fun submitDeleteData(): MutableLiveData<SubmitModel> {
        return targetMutableLiveData
    }

    fun getTargetData(): MutableLiveData<TargetResponse> {
        return mutableLiveData
    }

    fun getTarget(type: String, hospitalId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getTarget(type, hospitalId, accessToken)

    }

    fun getAllTarget(hospitalId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getAllTarget(hospitalId, accessToken)

    }

}
