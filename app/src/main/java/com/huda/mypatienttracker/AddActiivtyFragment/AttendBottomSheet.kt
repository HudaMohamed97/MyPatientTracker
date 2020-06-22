package com.huda.mypatienttracker.AddActiivtyFragment

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.huda.mypatienttracker.Adapters.CustomSheetSpecialityAdapter
import com.huda.mypatienttracker.Models.SpecialityAteendesModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.add_speaker_fragment.*
import kotlinx.android.synthetic.main.attend_fragment.*
import kotlinx.android.synthetic.main.attend_fragment.swip_shortcut
import java.util.*

class AttendBottomSheet(
    val attandanceList: ArrayList<String>,
    val specialityRequestedList: ArrayList<String>
) :
    BottomSheetDialogFragment() {
    private lateinit var root: View
    lateinit var attendanceListener: AttendanceListener
    private lateinit var specialitySpinner: SpinnerDialog
    private lateinit var speakersAdapter: CustomSheetSpecialityAdapter
    private lateinit var recyclerView: RecyclerView
    private val specialityList = arrayListOf<String>()
    private val modelList = arrayListOf<SpecialityAteendesModel>()


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
        initListOfRecycler()
        initRecyclerView()
        swip_shortcut.setOnClickListener {
            dismiss()
        }
        intializeSpecialitySpinner()
        addNumOfAttend.setOnClickListener {
            if (numOfAttend.text.toString().isEmpty() || SpecialitySpinner.text.toString()
                    .isEmpty() || SpecialitySpinner.text.toString() == "Speciality"
            ) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                modelList.add(
                    SpecialityAteendesModel(
                        numOfAttend.text.toString(),
                        SpecialitySpinner.text.toString()
                    )
                )
                if (attendanceListener != null) {
                    val number = numOfAttend.text.toString()
                    attandanceList.add(number)
                    attendanceListener.onAttendedAdd(attandanceList, specialityRequestedList)
                    speakersAdapter.notifyItemInserted(modelList.size - 1)
                    speakersAdapter.notifyDataSetChanged()
                    Toast.makeText(activity, "Added successfully Thanks", Toast.LENGTH_SHORT).show()
                    resetData()


                }
            }
        }
        SpecialitySpinner.setOnClickListener {
            if (specialityList.size != 0) {
                specialitySpinner.showSpinerDialog()
            }
        }

    }

    private fun resetData() {
        numOfAttend.setText("")
        numOfAttend.hint = "   Number of Attendees"
        SpecialitySpinner.text = "Speciality"
    }

    private fun initRecyclerView() {
        recyclerView = root.findViewById(R.id.specialityRecycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        speakersAdapter = CustomSheetSpecialityAdapter(modelList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = speakersAdapter
        speakersAdapter.setOnCommentListener(object :
            CustomSheetSpecialityAdapter.OnCommentClickListener {
            override fun onDeleteImageClicked(position: Int) {
                modelList.removeAt(position)
                attandanceList.removeAt(position)
                specialityRequestedList.removeAt(position)
                speakersAdapter.notifyDataSetChanged()
                attendanceListener.onAttendedAdd(attandanceList, specialityRequestedList)
            }


        })
    }

    private fun initListOfRecycler() {
        for ((position, speciality) in specialityRequestedList.withIndex()) {
            modelList.add(SpecialityAteendesModel(attandanceList[position], speciality))
        }

    }

    private fun intializeSpecialitySpinner() {
        specialityList.clear()
        specialityList.add("PH")
        specialityList.add("RH")
        specialityList.add("Cardio")
        specialityList.add("Pharmacist")
        initializeSpecialitySpinner(specialityList)
    }


    private fun initializeSpecialitySpinner(
        typeList: ArrayList<String>
    ) {
        specialitySpinner = SpinnerDialog(activity!!, typeList, "") // With No Animation
        specialitySpinner.setCancellable(true) // for cancellable
        specialitySpinner.setShowKeyboard(false) // for open keyboard by default
        specialitySpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                val specialityText = typeList[position]
                specialityRequestedList.add(specialityText)
                SpecialitySpinner.text = specialityText
            }
        })
    }


    interface AttendanceListener {
        fun onAttendedAdd(
            number: ArrayList<String>,
            specialityRequestedList: ArrayList<String>
        )
    }

    fun setOnAttendAddedListener(attendanceListener: AttendanceListener) {
        this.attendanceListener = attendanceListener
    }


}


