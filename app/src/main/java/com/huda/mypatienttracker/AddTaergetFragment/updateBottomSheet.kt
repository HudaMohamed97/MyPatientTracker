package com.huda.mypatienttracker.AddTaergetFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.update_target_fragment.*

class updateBottomSheet :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    lateinit var attendanceListener: AttendanceListener
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var addTargetFragmentViewModel: AddTargetFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.update_target_fragment, container, false)
        addTargetFragmentViewModel =
            ViewModelProviders.of(this).get(AddTargetFragmentViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        updateTarget.setOnClickListener {
            if (targetNum.text.toString().isEmpty()) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                val number = targetNum.text.toString().toInt()
                if (attendanceListener != null) {
                    attendanceListener.onTargetUpdated(number)
                    dismiss()
                }
            }
        }

    }


    interface AttendanceListener {
        fun onTargetUpdated(number: Int)
    }

    fun setOnAttendAddedListener(attendanceListener: AttendanceListener) {
        this.attendanceListener = attendanceListener
    }


}


