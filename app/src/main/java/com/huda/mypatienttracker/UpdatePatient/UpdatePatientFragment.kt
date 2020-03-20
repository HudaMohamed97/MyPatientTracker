package com.huda.mypatienttracker.UpdatePatient

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
import kotlinx.android.synthetic.main.add_patient_fragment.*
import kotlinx.android.synthetic.main.add_patient_fragment.uptraviLayout
import kotlinx.android.synthetic.main.update_patient_fragment.*

class UpdatePatientFragment : Fragment() {
    companion object {
        val UPTRAVI: String = "UPTRAVIE"
        val OPSUMIT: String = "OPSUMIT"
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
        root = inflater.inflate(R.layout.update_patient_fragment, container, false)
        addPatientFragmentViewModel =
            ViewModelProviders.of(this).get(AddPatientFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PatientId = arguments?.getInt("PatientId")!!
        setClickListeners()
        callHospitals("coe", false, false)
        intializeEtiologySpinner()
    }

    private fun intializeEtiologySpinner() {
        etiologyList.clear()
        etiologyList.add("iPAH")
        etiologyList.add("CHD")
        etiologyList.add("CTP")
        etiologyList.add("PoPH")
        etiologyList.add("Others")
        initializeEtiologySpinner(updateEtiloigySpinner, etiologyList)
    }


    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        val rg = root.findViewById(R.id.updateRadioPatientGroup) as RadioGroup
        fromType = UPTRAVI
        otherMedication = "PDE5i"
        etiology = ""

        updatePDE5i.setOnClickListener {
            otherMedication = "PDE5i"
            updateOral_PC.isChecked = false
            updateRio.isChecked = false
            updateother.isChecked = false
        }
        updatePDE5iOpsumit.setOnClickListener {
            otherMedication = "PDE5i"
            updateMacitentan.isChecked = false
            updateRioopsumit.isChecked = false
            updateOther_ERA.isChecked = false
        }

        updateOral_PC.setOnClickListener {
            otherMedication = "Oral_PC"
            updatePDE5i.isChecked = false
            updateRio.isChecked = false
            updateother.isChecked = false
        }

        updateMacitentan.setOnClickListener {
            otherMedication = "Macitentan"
            updatePDE5iOpsumit.isChecked = false
            updateRioopsumit.isChecked = false
            updateOther_ERA.isChecked = false
        }

        updateOther_ERA.setOnClickListener {
            otherMedication = "Other_ERA"
            updatePDE5iOpsumit.isChecked = false
            updateRioopsumit.isChecked = false
            updateMacitentan.isChecked = false
        }

        updateRioopsumit.setOnClickListener {
            otherMedication = "Rioopsumit"
            updatePDE5iOpsumit.isChecked = false
            updateMacitentan.isChecked = false
            updateOther_ERA.isChecked = false
        }

        updateRio.setOnClickListener {
            otherMedication = "Rio"
            updateOral_PC.isChecked = false
            updatePDE5i.isChecked = false
            updateother.isChecked = false
        }

        updateother.setOnClickListener {
            otherMedication = "PDE5i"
            updateOral_PC.isChecked = false
            updateRio.isChecked = false
            updatePDE5i.isChecked = false
        }

        updateDoctorText.setOnClickListener {
            Toast.makeText(activity, "Please Select Hospital First Thanks.", Toast.LENGTH_SHORT)
                .show()
        }

        updateButton.setOnClickListener {
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
                R.id.updateRadioUptarvi -> {
                    fromType = UPTRAVI
                    uptraviLayout.visibility = View.VISIBLE
                    opsumitLayout.visibility = View.GONE
                }
                R.id.updateRadioOpsumit -> {
                    fromType = OPSUMIT
                    updateUptraviLayout.visibility = View.GONE
                    updateOpsumitLayout.visibility = View.VISIBLE
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
            updatepatientProgressBar.visibility = View.VISIBLE
        } else {
            updatepatientProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.getHospitals(type, accessToken)
        }
        addPatientFragmentViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                updatepatientProgressBar.visibility = View.GONE
            } else {
                hospitalList.clear()
                updatepatientProgressBar.visibility = View.GONE
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
            addPatientFragmentViewModel.getSingelHospital(hospitalId, accessToken)
        }
        addPatientFragmentViewModel.getSingelData().observe(this, Observer {
            if (it != null) {
                updateDoctorText.visibility = View.GONE
                updateDoctorSpinner.visibility = view?.visibility!!
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
        initializeDoctorSpinner(updateDrNameSpinner, doctorNameList)
    }


    private fun preparHospitalList(hopital_List: ArrayList<HospitalData>) {
        hospitalNameList.clear()
        for (hospital in hopital_List) {
            hospitalNameList.add(hospital.name)
        }
        initializeHospitalSpinner(updateHospitalspinner, hospitalNameList)
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
                hospitalId = hospitalList[position].id
                callSingelHospital(hospitalId, false)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            updateHospitalspinner.adapter = arrayAdapter
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