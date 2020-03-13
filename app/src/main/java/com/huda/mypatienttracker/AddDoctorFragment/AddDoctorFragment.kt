package com.huda.mypatienttracker.AddDoctorFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.huda.mypatienttracker.Models.AddDoctorModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_doctor.*
import kotlinx.android.synthetic.main.add_doctor.mainView

class AddDoctorFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addFragmentViewModel: AddFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val typeList = arrayListOf<String>()
    private var flagSelected: Int = 0
    private lateinit var typeSpeciality: String
    private var hospitalId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_doctor, container, false)
        addFragmentViewModel = ViewModelProviders.of(this).get(AddFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hospitalId = arguments?.getInt("hospitalId")!!
        setClickListeners()
        prepareSpecialityList()

    }

    private fun prepareSpecialityList() {
        typeList.clear()
        typeList.add("Expert Speaker")
        typeList.add("Raising Start")
        typeList.add("Just Referral")
        initializeSpecialitySpinner(specialityType, typeList)

    }

    private fun initializeSpecialitySpinner(
        spinnerType: SearchableSpinner,
        typeList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    typeList
                )
            }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                val typeHospital = typeList[position]
                typeSpeciality = when (typeHospital) {
                    "Expert Speaker" -> {
                        flagSelected = 1
                        "Expert Speaker"

                    }
                    "Raising Start" -> {
                        flagSelected = 1
                        "Raising Start"
                    }
                    "Raising Start" -> {
                        flagSelected = 1
                        "Raising Start"
                    }
                    "Just Referral" -> {
                        flagSelected = 1
                        "Just Referral"
                    }
                    else -> {
                        flagSelected = 0
                        "error"
                    }

                }
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

    private fun setClickListeners() {
        flagSelected = 0
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        addDoctorButton.setOnClickListener {
            when {
                DoctorName.text.isEmpty() || specialityName.text.isEmpty() -> Toast.makeText(
                    activity,
                    "Please fill All Fields Thanks",
                    Toast.LENGTH_SHORT
                ).show()
                flagSelected == 0 -> Toast.makeText(
                    activity,
                    "Please Choose Speaciality Type",
                    Toast.LENGTH_SHORT
                )
                    .show()
                else -> {
                    val model =
                        AddDoctorModel(
                            DoctorName.text.toString(),
                            specialityName.text.toString(),
                            typeSpeciality,
                            hospitalId
                        )
                    callAddDoctor(model)
                }
            }


        }

        mainView.setOnClickListener {
            hideKeyboard()
        }


    }

    private fun callAddDoctor(model: AddDoctorModel) {
        addDoctorProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addFragmentViewModel.addDoctor(model, accessToken)
        }
        addFragmentViewModel.submitData().observe(this, Observer {
            addDoctorProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.type == "error")
                    Toast.makeText(
                        activity,
                        it.title,
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}