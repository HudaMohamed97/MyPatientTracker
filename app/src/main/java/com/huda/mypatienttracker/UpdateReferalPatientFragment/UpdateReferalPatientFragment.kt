package com.huda.mypatienttracker.UpdateReferalPatientFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.AddPatientFragment.AddPatientFragmentViewModel
import com.huda.mypatienttracker.Models.Doctors
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.huda.mypatienttracker.Models.updatePatientRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.update_patient_fragment.*
import kotlinx.android.synthetic.main.update_preferal_fragment.*

class UpdateReferalPatientFragment : Fragment() {
    companion object {
        val UPTRAVI: String = "UPTRAVIE"
        val OPSUMIT: String = "OPSUMIT"
        val TRACLEER: String = "TRACLEER"
    }

    private lateinit var root: View
    private var fromType: String = ""
    private var etiology: String = ""
    private var otherMedication: String = ""
    private lateinit var addPatientFragmentViewModel: AddPatientFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val etiologyList = arrayListOf<String>()
    private val doctorist = arrayListOf<Doctors>()
    private val doctorNameList = arrayListOf<String>()
    private var hospitalId: Int = -1
    private var PatientId: Int = 0
    private var doctorId: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.update_preferal_fragment, container, false)
        addPatientFragmentViewModel =
            ViewModelProviders.of(this).get(AddPatientFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PatientId = arguments?.getInt("PatientId")!!
        setClickListeners()
        callHospitals("referal", false, false)
        intializeEtiologySpinner()
    }

    private fun intializeEtiologySpinner() {
        etiologyList.clear()
        etiologyList.add("iPAH")
        etiologyList.add("CHD")
        etiologyList.add("CTP")
        etiologyList.add("PoPH")
        etiologyList.add("Others")
        initializeEtiologySpinner(etiologyReferalSpinner, etiologyList)
    }


    private fun setClickListeners() {
        val rg = root.findViewById(R.id.radioPatientReferalGroup) as RadioGroup
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        fromType = UPTRAVI
        otherMedication = "PDE5i"
        etiology = ""

        ReferalPDE5i.setOnClickListener {
            otherMedication = "PDE5i"
            ReferalOral_PC.isChecked = false
            ReferalRio.isChecked = false
            Referalother.isChecked = false
        }
        ReferalOral_PC.setOnClickListener {
            otherMedication = "Oral_PC"
            ReferalPDE5i.isChecked = false
            ReferalRio.isChecked = false
            Referalother.isChecked = false
        }
        ReferalRio.setOnClickListener {
            otherMedication = "Rio"
            ReferalPDE5i.isChecked = false
            ReferalOral_PC.isChecked = false
            Referalother.isChecked = false
        }

        Referalother.setOnClickListener {
            otherMedication = "other"
            ReferalPDE5i.isChecked = false
            ReferalOral_PC.isChecked = false
            ReferalRio.isChecked = false
        }


        PDE5iReferalOpsumit.setOnClickListener {
            otherMedication = "PDE5i"
            ReferalMacitentan.isChecked = false
            RioReferalopsumit.isChecked = false
            ReferalOther_ERA.isChecked = false
        }

        ReferalMacitentan.setOnClickListener {
            otherMedication = "Macitentan"
            PDE5iReferalOpsumit.isChecked = false
            RioReferalopsumit.isChecked = false
            ReferalOther_ERA.isChecked = false
        }

        ReferalOther_ERA.setOnClickListener {
            otherMedication = "Other_ERA"
            PDE5iReferalOpsumit.isChecked = false
            RioReferalopsumit.isChecked = false
            ReferalMacitentan.isChecked = false
        }

        RioReferalopsumit.setOnClickListener {
            otherMedication = "Rioopsumit"
            PDE5iReferalOpsumit.isChecked = false
            ReferalMacitentan.isChecked = false
            ReferalOther_ERA.isChecked = false
        }


        continueReferalButton.setOnClickListener {
            if (fromType == "" || hospitalId == -1 || doctorId == -1 || otherMedication == "" || etiology == "") {
                Toast.makeText(activity, "Please fill All Fields Thanks", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val model =
                    updatePatientRequestModel(fromType, etiology, otherMedication)
                callUpdatePatient(PatientId, model)
            }
        }

        rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioReferalUptarvi -> {
                    fromType = UPTRAVI
                    uptraviReferalLayout.visibility = View.VISIBLE
                    ReferalopsumitLayout.visibility = View.GONE
                }
                R.id.radiotrReferalCeller -> {
                    fromType = OPSUMIT
                    uptraviReferalLayout.visibility = View.GONE
                    ReferalopsumitLayout.visibility = View.VISIBLE
                }
                R.id.radioReferalOpsumit -> {
                    fromType = TRACLEER
                    uptraviReferalLayout.visibility = View.GONE
                    ReferalopsumitLayout.visibility = View.VISIBLE
                }
            }
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

    }

    private fun callUpdatePatient(PatientId: Int, model: updatePatientRequestModel) {
        updatepatientProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.updatePatient(PatientId, model, accessToken)
        }
        addPatientFragmentViewModel.getUpdatePatient().observe(this, Observer {
            updatepatientProgressBar.visibility = View.GONE
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

    private fun callHospitals(
        type: String,
        fromLoadMore: Boolean,
        fromRefresh: Boolean
    ) {
        if (fromLoadMore) {
            referalProgressBar.visibility = View.VISIBLE
        } else {
            referalProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.getHospitals(type, accessToken)
        }
        addPatientFragmentViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                referalProgressBar.visibility = View.GONE
            } else {
                hospitalList.clear()
                referalProgressBar.visibility = View.GONE
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

    private fun callSingelHospital(
        hospitalId: Int,
        fromLoadMore: Boolean
    ) {
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            doctorProgressBar.visibility = View.VISIBLE
            addPatientFragmentViewModel.getSingelHospital(hospitalId, accessToken)
        }
        addPatientFragmentViewModel.getSingelData().observe(this, Observer {
            doctorProgressBar.visibility = View.GONE
            if (it != null) {
                doctorist.clear()
                for (data in it.data.doctors) {
                    doctorist.add(data)
                }
                preparDoctorsList(doctorist)
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun preparDoctorsList(doctorist: ArrayList<Doctors>) {
        doctorNameList.clear()
        for (hospital in doctorist) {
            doctorNameList.add(hospital.name)
        }
        initializeDoctorSpinner(ReferalDoctorSpinner, doctorNameList)
    }


    private fun preparHospitalList(hopital_List: ArrayList<HospitalData>) {
        hospitalNameList.clear()
        for (hospital in hopital_List) {
            hospitalNameList.add(hospital.name)
        }
        initializeHospitalSpinner(ReferalHospitalspinner, hospitalNameList)
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

        ReferalHospitalspinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    hospitalId = hospitalList[position].id
                    callSingelHospital(hospitalId, false)
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // your code here
                }
            }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            ReferalHospitalspinner.adapter = arrayAdapter
        }

    }

    private fun initializeEtiologySpinner(
        spinner: SearchableSpinner,
        EtiologyList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    EtiologyList
                )
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                etiology = etiologyList[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinner.adapter = arrayAdapter
        }

    }

    private fun initializeDoctorSpinner(
        spinner: SearchableSpinner,
        doctorsNameList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    doctorsNameList
                )
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                doctorId = doctorist[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinner.adapter = arrayAdapter
        }

    }
}