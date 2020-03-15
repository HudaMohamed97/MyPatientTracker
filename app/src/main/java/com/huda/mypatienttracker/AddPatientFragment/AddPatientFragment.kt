package com.huda.mypatienttracker.AddPatientFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.login_fragment.*
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.Models.Cities
import com.huda.mypatienttracker.Models.CountryData
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_patient_fragment.*
import kotlinx.android.synthetic.main.hospital_fragment_list.*


class AddPatientFragment : Fragment() {
    companion object {
        val UPTRAVI: String = "UPTRAVIE"
        val OPSUMIT: String = "OPSUMIT"
    }

    private lateinit var root: View
    private var fromType: String = ""
    private lateinit var addPatientFragmentViewModel: AddPatientFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val doctorist = arrayListOf<Cities>()
    private val doctorNameList = arrayListOf<String>()
    private var hospitalId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_patient_fragment, container, false)
        addPatientFragmentViewModel =
            ViewModelProviders.of(this).get(AddPatientFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        callHospitals("coe",false,false)
    }

    private fun setClickListeners() {

        val rg = root.findViewById(R.id.radioPatientGroup) as RadioGroup

        continueButton.setOnClickListener {
            if (fromType == UPTRAVI) {
                if (findNavController().currentDestination?.id == R.id.addPatientFragment) {
                    findNavController().navigate(R.id.action_PatientList_toUptravi)
                }

            } else if (fromType == OPSUMIT) {
                if (findNavController().currentDestination?.id == R.id.addPatientFragment) {
                    findNavController().navigate(R.id.action_PatientList_toOpsumit)

                } else if (fromType == "") {
                    Toast.makeText(activity, "Please Select Patient Type", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioUptarvi -> {
                    fromType = UPTRAVI
                    uptraviLayout.visibility = View.VISIBLE
                    opsumitLayout.visibility = View.GONE
                }
                R.id.radioOpsumit -> {
                    fromType = OPSUMIT
                    uptraviLayout.visibility = View.GONE
                    opsumitLayout.visibility = View.VISIBLE
                }
            }
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

    }

    private fun callHospitals(
        type: String,
        fromLoadMore: Boolean,
        fromRefresh: Boolean
    ) {
        if (fromLoadMore) {
            patientProgressBar.visibility = View.VISIBLE
        } else {
            patientProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.getHospitals(type, accessToken)
        }
        addPatientFragmentViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                patientProgressBar.visibility = View.GONE
            } else {
                hospitalList.clear()
                patientProgressBar.visibility = View.GONE
            }
            if (it != null) {
                hospitalList.clear()
                for (data in it.data) {
                    hospitalList.add(data)
                }
                preparHospitalList(hospitalList)
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun preparHospitalList(hopital_List: ArrayList<HospitalData>) {
        hospitalNameList.clear()
        for (hospital in hopital_List) {
            hospitalNameList.add(hospital.name)
        }
        initializeHospitalSpinner(Hospitalspinner, hospitalNameList)
    }

    private fun initializeHospitalSpinner(
        countrySpinner: SearchableSpinner,
        countriesNameList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    countriesNameList
                )
            }

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                val cml = parentView.getItemAtPosition(position).toString()
                hospitalId = hospitalList[position].id
                //callDoctorsPerHospital(hospitalId)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }


        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            Hospitalspinner.adapter = arrayAdapter
        }

    }

    private fun callDoctorsPerHospital(hospitalId: Int) {

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