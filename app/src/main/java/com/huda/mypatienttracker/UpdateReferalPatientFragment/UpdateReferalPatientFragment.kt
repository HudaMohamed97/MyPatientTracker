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
import com.huda.mypatienttracker.Models.updateReferalPatientRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.update_preferal_fragment.*

class UpdateReferalPatientFragment : Fragment() {
    companion object {
        val UPTRAVI: String = "UPTRAVIE"
        val OPSUMIT: String = "OPSUMIT"
        val TRACLEER: String = "TRACLEER"
    }

    private lateinit var root: View
    private var fromType: String? = null
    private var etiology: String? = null
    private var otherMedication: String? = null
    private lateinit var addPatientFragmentViewModel: AddPatientFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val etiologyList = arrayListOf<String>()
    private val doctorist = arrayListOf<Doctors>()
    private val doctorNameList = arrayListOf<String>()
    private val doctorReferalList = arrayListOf<Doctors>()
    private val doctorReferalNameList = arrayListOf<String>()
    private var toHospitalId: Int = -1
    private var PatientId: Int = 0
    private var HospitalName: String = ""
    private var HospitalId: Int = 0
    private var doctorId: Int = -1
    private var doctorReferalId: Int = -1


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
        HospitalId = arguments?.getInt("HospitalId")!!
        HospitalName = arguments?.getString("HospitalName")!!
        doctorReferalId = arguments?.getInt("DoctorId")!!
        setClickListeners()
        callHospitals("coe", false, false)
        callReferalSingelHospital(8, false)
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
        ReferalHospitalName.text = "   " + HospitalName
        val rg = root.findViewById(R.id.radioPatientReferalGroup) as RadioGroup
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
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
            if (toHospitalId == -1) {
                val model = updateReferalPatientRequestModel(
                    HospitalId, doctorReferalId,
                    null,
                    null,
                    fromType,
                    etiology,
                    otherMedication
                )
                callUpdatePatient(PatientId, model)
            } else {
                val model = updateReferalPatientRequestModel(
                    HospitalId, doctorReferalId,
                    toHospitalId,
                    doctorId,
                    fromType,
                    etiology,
                    otherMedication
                )
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

    private fun callUpdatePatient(PatientId: Int, model: updateReferalPatientRequestModel) {
        updateReferalProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.updateReferalPatient(PatientId, model, accessToken)
        }
        addPatientFragmentViewModel.getUpdateReferalPatient().observe(this, Observer {
            updateReferalProgressBar.visibility = View.GONE
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
            addPatientFragmentViewModel.getHospitals(1, type, accessToken)
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

    private fun callReferalSingelHospital(
        hospitalId: Int,
        fromLoadMore: Boolean
    ) {
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addPatientFragmentViewModel.getSingelHospital(hospitalId, accessToken)
        }
        addPatientFragmentViewModel.getSingelData().observe(this, Observer {
            if (it != null) {
                doctorReferalList.clear()
                for (data in it.data.doctors) {
                    doctorReferalList.add(data)
                }
                preparReferalDoctorsList(doctorReferalList)
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
        initializeDoctorSpinner(ReferalToDrNameSpinner, doctorNameList)
    }

    private fun preparReferalDoctorsList(doctorist: ArrayList<Doctors>) {
        doctorReferalNameList.clear()
        for (hospital in doctorist) {
            doctorReferalNameList.add(hospital.name)
        }
        initializeReferalDoctorSpinner(ReferalDoctorSpinner, doctorReferalNameList)
    }


    private fun preparHospitalList(hopital_List: ArrayList<HospitalData>) {
        hospitalNameList.clear()
        for (hospital in hopital_List) {
            hospitalNameList.add(hospital.name)
        }
        initializeHospitalSpinner(ReferaltoHospitalSpinner, hospitalNameList)
    }

    private fun initializeHospitalSpinner(
        spinner: SearchableSpinner,
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

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    toHospitalId = hospitalList[position].id
                    callSingelHospital(toHospitalId, false)
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

    private fun initializeReferalDoctorSpinner(
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
                doctorReferalId = doctorReferalList[position].id
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