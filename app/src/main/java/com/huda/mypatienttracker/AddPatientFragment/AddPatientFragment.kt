package com.huda.mypatienttracker.AddPatientFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.huda.mypatienttracker.R
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.Models.Doctors
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.huda.mypatienttracker.Models.PatientRequestModel
import com.huda.mypatienttracker.UpdatePatient.UpdatePatientFragment
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_patient_fragment.*
import kotlinx.android.synthetic.main.update_patient_fragment.*


class AddPatientFragment : Fragment() {
    companion object {
        val UPTRAVI: String = "UPTRAVIE"
        val OPSUMIT: String = "OPSUMIT"
        val TRACLEER: String = "tracleer"

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
    private var doctorId: Int = -1


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
        initializeEtiologySpinner(etiloigySpinner, etiologyList)
    }


    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val rg = root.findViewById(R.id.radioPatientGroup) as RadioGroup
        fromType = UPTRAVI
        otherMedication = "PDE5i"
        etiology = ""


        PDE5i.setOnClickListener {
            otherMedication = "PDE5i"
            Oral_PC.isChecked = false
            Rio.isChecked = false
            other.isChecked = false
        }
        PDE5iOpsumit.setOnClickListener {
            otherMedication = "PDE5i"
            Macitentan.isChecked = false
            Rioopsumit.isChecked = false
            Other_ERA.isChecked = false
        }

        Oral_PC.setOnClickListener {
            otherMedication = "Oral_PC"
            PDE5i.isChecked = false
            Rio.isChecked = false
            other.isChecked = false
        }

        Macitentan.setOnClickListener {
            otherMedication = "Macitentan"
            PDE5iOpsumit.isChecked = false
            Rioopsumit.isChecked = false
            Other_ERA.isChecked = false
        }

        Other_ERA.setOnClickListener {
            otherMedication = "Other_ERA"
            PDE5iOpsumit.isChecked = false
            Rioopsumit.isChecked = false
            Macitentan.isChecked = false
        }

        Rioopsumit.setOnClickListener {
            otherMedication = "Rioopsumit"
            PDE5iOpsumit.isChecked = false
            Macitentan.isChecked = false
            Other_ERA.isChecked = false
        }

        Rio.setOnClickListener {
            otherMedication = "Rio"
            Oral_PC.isChecked = false
            PDE5i.isChecked = false
            other.isChecked = false
        }

        other.setOnClickListener {
            otherMedication = "PDE5i"
            Oral_PC.isChecked = false
            Rio.isChecked = false
            PDE5i.isChecked = false
        }

        doctorText.setOnClickListener {
            Toast.makeText(activity, "Please Select Hospital First Thanks.", Toast.LENGTH_SHORT)
                .show()
        }

        continueButton.setOnClickListener {
            if (fromType == "" || hospitalId == -1 || doctorId == -1 || otherMedication == "" || etiology == "") {
                Toast.makeText(activity, "Please fill All Fields Thanks", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val model = PatientRequestModel(
                    "patient",
                    1,
                    1,
                    hospitalId,
                    doctorId,
                    fromType,
                    etiology,
                    otherMedication
                )
                callAddPatient(model)
            }
        }

        rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioUptarvi -> {
                    fromType = UPTRAVI
                    uptraviLayout.visibility = View.VISIBLE
                    opsumitLayout.visibility = View.GONE
                }
                R.id.addRadioTraceller -> {
                    fromType = TRACLEER
                    uptraviLayout.visibility = View.GONE
                    opsumitLayout.visibility = View.VISIBLE
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

    private fun callAddPatient(model: PatientRequestModel) {
        patientProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.submitPatient(model, accessToken)
        }
        addPatientFragmentViewModel.getSubmitPatient().observe(this, Observer {
            patientProgressBar.visibility = View.GONE
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
            patientProgressBar.visibility = View.VISIBLE
        } else {
            patientProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.getHospitals(1, type, accessToken)
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
                doctorText.visibility = View.GONE
                doctorSpinner.visibility = view?.visibility!!
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
        initializeDoctorSpinner(drNameSpinner, doctorNameList)
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
                hospitalId = hospitalList[position].id
                callSingelHospital(hospitalId, false)
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

        drNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
            drNameSpinner.adapter = arrayAdapter
        }

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