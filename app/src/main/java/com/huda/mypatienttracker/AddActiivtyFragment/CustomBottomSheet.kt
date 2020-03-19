package com.huda.mypatienttracker.AddActiivtyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.huda.mypatienttracker.Models.SpeakerRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_speaker_fragment.*
import java.util.ArrayList

class CustomBottomSheet :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    lateinit var speakerClickListener: OnSpeakerAddedListener
    private val speakerTypeList = arrayListOf<String>()
    private var speakerInterType = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_speaker_fragment, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intializeSpeakerInterType()
        addSpeaker.setOnClickListener {
            if (speakerName.text.toString().isEmpty() || speakerSpeciality.text.toString().isEmpty() || speakerInterType == "") {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                if (speakerClickListener != null) {
                    val model = SpeakerRequestModel(
                        "international",
                        speakerInterType,
                        speakerName.text.toString(),
                        speakerSpeciality.text.toString()
                    )
                    speakerClickListener.onSpeakerAdded(model)
                    Toast.makeText(activity, "Added successfully Thanks", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }

    }

    interface OnSpeakerAddedListener {
        fun onSpeakerAdded(speakerRequestModel: SpeakerRequestModel)
    }

    fun setOnSpeakerAddedListener(onCommentClickListener: OnSpeakerAddedListener) {
        this.speakerClickListener = onCommentClickListener
    }

    private fun intializeSpeakerInterType() {
        speakerTypeList.clear()
        speakerTypeList.add("Expert Speaker")
        speakerTypeList.add("Raising Start")
        initializeInterTypeSpinner(speakerTypeSpinner, speakerTypeList)
    }

    private fun initializeInterTypeSpinner(
        spinnerType: SearchableSpinner,
        speakerTypeList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    speakerTypeList
                )
            }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                speakerInterType = speakerTypeList[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinnerType.adapter = arrayAdapter
        }
    }


}


