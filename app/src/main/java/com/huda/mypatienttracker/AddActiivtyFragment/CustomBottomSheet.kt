package com.huda.mypatienttracker.AddActiivtyFragment

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.huda.mypatienttracker.ActivityFragment.AddActivityViewModel
import com.huda.mypatienttracker.Adapters.CustomSheetActivityAdapter
import com.huda.mypatienttracker.Models.DoctorDate
import com.huda.mypatienttracker.Models.SpeakerRequestModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.add_speaker_fragment.*
import java.util.*

class CustomBottomSheet(val speakerRequestList: ArrayList<SpeakerRequestModel>) :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    private val speakerList = arrayListOf<String>()
    lateinit var speakerClickListener: OnSpeakerAddedListener
    private val speakerTypeList = arrayListOf<String>()
    private var speakerInterType = ""
    private var type = "international"
    private lateinit var speakersAdapter: CustomSheetActivityAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var localSpeakersSpinner: SpinnerDialog
    private lateinit var spinnerType: SpinnerDialog
    private val doctorNameList = arrayListOf<String>()
    private lateinit var addActivityViewModel: AddActivityViewModel
    private val DoctorList = arrayListOf<DoctorDate>()
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var seakerspinner: SpinnerDialog
    private var speakerType = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_speaker_fragment, container, false)
        addActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        initRecyclerView()
        intializeSpeakerInterType()
        intializeSpeakerSpinner()
        speakersLocalSpinner.setOnClickListener {
            if (doctorNameList.size != 0) {
                localSpeakersSpinner.showSpinerDialog()
            } else {
                Toast.makeText(
                    activity,
                    "Please Select Type Of speaker First..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        speaker_Spinner.setOnClickListener {
            if (speakerList.size != 0) {
                initializeSpeakerSpinner(speakerList)
                seakerspinner.showSpinerDialog()
            }
        }
        swip_shortcut.setOnClickListener {
            dismiss()
        }
        speakerTypeSpinner.setOnClickListener {
            if (speakerTypeList.size != 0) {
                spinnerType.showSpinerDialog()
            }
        }

        addSpeaker.setOnClickListener {
            if (speakerName.text.toString().isEmpty() || speakerSpeciality.text.toString()
                    .isEmpty() || speakerInterType == ""
            ) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                if (speakerClickListener != null) {
                    val model = SpeakerRequestModel(
                        type,
                        speakerInterType,
                        speakerSpeciality.text.toString(),
                        speakerName.text.toString()
                    )
                    speakerRequestList.add(model)
                    speakerClickListener.onSpeakerAdded(speakerRequestList)
                    speakersAdapter.notifyItemInserted(speakerRequestList.size - 1)
                    speakersAdapter.notifyDataSetChanged()
                    Toast.makeText(activity, "Added successfully Thanks", Toast.LENGTH_SHORT).show()
                    resetData()
                }
            }
        }

    }

    private fun resetData() {
        speakerName.setText("")
        speakerName.hint = "   speakerName"
        speakerSpeciality.setText("")
        speakerSpeciality.hint = "   speakerSpeciality"
        speaker_Spinner.text = "Speaker"
        speakerTypeSpinner.text = "Speaker Type"
        speakerInterType = ""
        localSpeaker.visibility = View.GONE
    }

    private fun initRecyclerView() {
        recyclerView = root.findViewById(R.id.ActivtyRecycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        speakersAdapter = CustomSheetActivityAdapter(speakerRequestList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = speakersAdapter
        speakersAdapter.setOnCommentListener(object :
            CustomSheetActivityAdapter.OnCommentClickListener {
            override fun onDeleteImageClicked(position: Int) {
                speakerRequestList.removeAt(position)
                speakersAdapter.notifyDataSetChanged()
                speakerClickListener.onSpeakerAdded(speakerRequestList)
            }


        })
    }


    private fun intializeSpeakerSpinner() {
        speakerList.clear()
        speakerList.add("Inter")
        speakerList.add("Local")
    }


    interface OnSpeakerAddedListener {
        fun onSpeakerAdded(speakerRequestModel: ArrayList<SpeakerRequestModel>)
    }

    fun setOnSpeakerAddedListener(onCommentClickListener: OnSpeakerAddedListener) {
        this.speakerClickListener = onCommentClickListener
    }

    private fun callDoctors() {
        DoctorList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            speakerProgressBar.visibility = View.VISIBLE
            addActivityViewModel.getDoctors(accessToken)
        }
        addActivityViewModel.getDoctorData().observe(this, Observer {
            if (it != null) {
                speakerProgressBar.visibility = View.GONE
                for (doctor in it.data) {
                    DoctorList.add(doctor)
                }
                prepareDoctorList(DoctorList)
            }
        })
    }

    private fun showLocalLayout() {
        callDoctors()
        speakerSpeciality.isEnabled = false
        speakerName.isEnabled = false
        localSpeaker.visibility = View.VISIBLE

    }

    private fun hideLocalLayout() {
        type = "international"
        speakerSpeciality.isEnabled = true
        speakerName.isEnabled = true
        speakerSpeciality.setText("")
        speakerName.setText("")
        speakerTypeSpinner.text = "Speaker Type"
        localSpeaker.visibility = View.GONE

    }

    private fun initializeSpeakerSpinner(
        typeList: ArrayList<String>
    ) {
        seakerspinner = SpinnerDialog(activity!!, typeList, "") // With No Animation
        seakerspinner.setCancellable(true) // for cancellable
        seakerspinner.setShowKeyboard(false) // for open keyboard by default
        seakerspinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                val typeSpeaker = typeList[position]
                speakerType = when (typeSpeaker) {
                    "Inter" -> {
                        speaker_Spinner.text = "International"
                        hideLocalLayout()
                        "Inter"
                    }
                    else -> {
                        speaker_Spinner.text = "Local"
                        showLocalLayout()
                        "Local"
                    }

                }
            }
        })
    }


    private fun intializeSpeakerInterType() {
        speakerTypeList.clear()
        speakerTypeList.add("Expert Speaker")
        speakerTypeList.add("Raising Start")
        initializeInterTypeSpinner(speakerTypeList)
    }

    private fun prepareDoctorList(doctorList: ArrayList<DoctorDate>) {
        doctorNameList.clear()
        for (doctor in doctorList) {
            doctorNameList.add(doctor.name)
        }
        initializeDoctorSpinner(doctorNameList)
    }

    private fun initializeDoctorSpinner(
        doctorsNameList: ArrayList<String>
    ) {
        localSpeakersSpinner = SpinnerDialog(activity!!, doctorsNameList, "") // With No Animation
        localSpeakersSpinner.setCancellable(true) // for cancellable
        localSpeakersSpinner.setShowKeyboard(false) // for open keyboard by default
        localSpeakersSpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                speakersLocalSpinner.text = (doctorsNameList[position])
                speakerName.setText(DoctorList[position].name)
                speakerSpeciality.isEnabled = true
                speakerName.isEnabled = true
                speakerTypeSpinner.text = DoctorList[position].type
                speakerInterType = DoctorList[position].type
                speakerSpeciality.setText(DoctorList[position].speciality)
                type = "Local"

            }
        })
    }


    private fun initializeInterTypeSpinner(
        speakerTypeList: ArrayList<String>
    ) {
        spinnerType = SpinnerDialog(activity!!, speakerTypeList, "") // With No Animation
        spinnerType.setCancellable(true) // for cancellable
        spinnerType.setShowKeyboard(false) // for open keyboard by default
        spinnerType.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                speakerInterType = speakerTypeList[position]
                speakerTypeSpinner.text = speakerTypeList[position]

            }
        })


    }


}