package com.huda.mypatienttracker.AddActiivtyFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.SubmitModel
import com.huda.mypatienttracker.Models.AddActivityRequestModel

class AddActivityViewModel : ViewModel() {
    private var repositoryHelper: AddActivityFragment = AddActivityFragment()
    private lateinit var mutableLiveData: MutableLiveData<SubmitModel>
/*

    fun addDoctor(model: AddActivityRequestModel, accessToken: String) {
        mutableLiveData = repositoryHelper.addActivity(model, accessToken)

    }

    fun submitData(): MutableLiveData<SubmitModel> {
        return mutableLiveData
    }

*/

}
