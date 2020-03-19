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
import kotlinx.android.synthetic.main.add_speaker_fragment.addSpeaker
import kotlinx.android.synthetic.main.attend_fragment.*
import java.util.ArrayList

class AttendBottomSheet :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    lateinit var attendanceListener: AttendanceListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.attend_fragment, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNumOfAttend.setOnClickListener {
            if (numOfAttend.text.toString().isEmpty()) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                if (attendanceListener != null) {
                    val number = numOfAttend.text.toString()
                    attendanceListener.onAttendedAdd(number)
                    Toast.makeText(activity, "Added successfully Thanks", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }

    }

    interface AttendanceListener {
        fun onAttendedAdd(number: String)
    }

    fun setOnAttendAddedListener(attendanceListener: AttendanceListener) {
        this.attendanceListener = attendanceListener
    }


}


