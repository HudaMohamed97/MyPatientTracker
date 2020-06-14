package com.huda.mypatienttracker.UpdatePatient

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
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
import com.huda.mypatienttracker.Models.PatientResponseData
import com.huda.mypatienttracker.Models.updatePatientRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_patient_fragment.*
import kotlinx.android.synthetic.main.patient_fragment_list.*
import kotlinx.android.synthetic.main.update_patient_fragment.*

class UpdatePatientFragment : Fragment() {
    companion object {
        val UPTRAVI: String = "uptravi"
        val OPSUMIT: String = "opsumit"
        val TRACLEER: String = "tracleer"
    }

    private lateinit var root: View
    private var fromType: String = ""
    private lateinit var doctorSpinner: SpinnerDialog
    private lateinit var hospitalSpinner: SpinnerDialog
    private lateinit var etiologySpinner: SpinnerDialog
    private var etiology: String = ""
    private var otherMedication: String = ""
    private lateinit var addPatientFragmentViewModel: AddPatientFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val etiologyList = arrayListOf<String>()
    private val doctorlist = arrayListOf<Doctors>()
    private val doctorNameList = arrayListOf<String>()
    private var hospitalId: Int = -1
    private lateinit var PatientModel: PatientResponseData
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
        PatientModel = arguments?.getParcelable("PatientModel")!!
        hospitalId = PatientModel.hospital!!.id
        updateHospitalspinner.text = PatientModel.hospital!!.name
        etiology = PatientModel.last_treatment.etiology
        updateEtiloigySpinner.text = PatientModel.last_treatment.etiology
        doctorId = PatientModel.doctor.id
        updateDrNameSpinner.text = PatientModel.doctor.name

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
        initializeEtiologySpinner(etiologyList)
    }


    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        updateDrNameSpinner.setOnClickListener {
            if (doctorNameList.size != 0) {
                doctorSpinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please choose Hospital First", Toast.LENGTH_SHORT).show()

            }
        }
        updateHospitalspinner.setOnClickListener {
            if (hospitalNameList.size != 0) {
                hospitalSpinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please Wait", Toast.LENGTH_SHORT).show()

            }
        }
        updateEtiloigySpinner.setOnClickListener {
            if (etiologyList.size != 0) {
                etiologySpinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please Wait", Toast.LENGTH_SHORT).show()

            }
        }


        val rg = root.findViewById(R.id.updateRadioPatientGroup) as RadioGroup
        fromType = UPTRAVI
        updatePDE5i.setOnClickListener {
            otherMedication = "PDE5i"
            /*  updateOral_PC.isChecked = false
              updateRio.isChecked = false
              updateother.isChecked = false*/
        }
        updatePDE5iOpsumit.setOnClickListener {
            otherMedication = "PDE5i"
            /* updateMacitentan.isChecked = false
             updateRioopsumit.isChecked = false
             updateOther_ERA.isChecked = false*/
        }

        updateOral_PC.setOnClickListener {
            otherMedication = "Oral_PC"
            /*  updatePDE5i.isChecked = false
              updateRio.isChecked = false
              updateother.isChecked = false*/
        }

        updateMacitentan.setOnClickListener {
            otherMedication = "Macitentan"
            /* updatePDE5iOpsumit.isChecked = false
             updateRioopsumit.isChecked = false
             updateOther_ERA.isChecked = false*/
        }

        updateOther_ERA.setOnClickListener {
            otherMedication = "Other_ERA"
            /* updatePDE5iOpsumit.isChecked = false
             updateRioopsumit.isChecked = false
             updateMacitentan.isChecked = false*/
        }

        updateRioopsumit.setOnClickListener {
            otherMedication = "Rioopsumit"
            /* updatePDE5iOpsumit.isChecked = false
             updateMacitentan.isChecked = false
             updateOther_ERA.isChecked = false*/
        }

        updateRio.setOnClickListener {
            otherMedication = "Rio"
            /*  updateOral_PC.isChecked = false
              updatePDE5i.isChecked = false
              updateother.isChecked = false*/
        }

        updateother.setOnClickListener {
            otherMedication = "PDE5i"
            /* updateOral_PC.isChecked = false
             updateRio.isChecked = false
             updatePDE5i.isChecked = false*/
        }

        updateButton.setOnClickListener {
            if (updatePDE5i.isChecked) {
                otherMedication = "PDE5i,"
            }
            if (updatePDE5iOpsumit.isChecked) {
                otherMedication += "PDE5i,"
            }
            if (updateOral_PC.isChecked) {
                otherMedication += "Oral_PC,"
            }
            if (updateMacitentan.isChecked) {
                otherMedication += "Macitentan,"
            }
            if (updateOther_ERA.isChecked) {
                otherMedication += "Other_ERA,"
            }
            if (updateRioopsumit.isChecked) {
                otherMedication += "Rioopsumit,"
            }
            if (updateRio.isChecked) {
                otherMedication += "Rio,"
            }
            if (updateother.isChecked) {
                otherMedication += "other,"
            }
            if (otherMedication.length != 0) {
                otherMedication = otherMedication.substring(0, otherMedication.length - 1);

            }
            if (fromType == "" || hospitalId == -1 || doctorId == -1 || otherMedication == "" || etiology == "") {
                Toast.makeText(activity, "Please fill All Fields Thanks", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val model =
                    updatePatientRequestModel(fromType, etiology, otherMedication)
                callUpdatePatient(PatientModel.id, model)
            }
        }

        rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.updateRadioUptarvi -> {
                    fromType = UPTRAVI
                    updateUptraviLayout.visibility = View.VISIBLE
                    updateOpsumitLayout.visibility = View.GONE
                }
                R.id.updateRadioTraceller -> {
                    fromType = TRACLEER
                    updateUptraviLayout.visibility = View.GONE
                    updateOpsumitLayout.visibility = View.VISIBLE
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
            addPatientFragmentViewModel.getHospitals(1, type, accessToken)
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
                updateDoctorSpinner.visibility = view?.visibility!!
                doctorlist.clear()
                for (data in it.data.doctors) {
                    doctorlist.add(data)
                }
                preparDoctorsList(doctorlist)
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
        initializeDoctorSpinner(doctorNameList)
    }


    private fun preparHospitalList(hopital_List: ArrayList<HospitalData>) {
        hospitalNameList.clear()
        for (hospital in hopital_List) {
            hospitalNameList.add(hospital.name)
        }
        initializeHospitalSpinner(hospitalNameList)
    }


    private fun initializeHospitalSpinner(
        hospitalNameList: ArrayList<String>
    ) {
        hospitalSpinner = SpinnerDialog(activity!!, hospitalNameList, "") // With No Animation
        hospitalSpinner.setCancellable(true) // for cancellable
        hospitalSpinner.setShowKeyboard(false) // for open keyboard by default
        hospitalSpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                hospitalId = hospitalList[position].id
                callSingelHospital(hospitalId, false)
                updateHospitalspinner.text = hospitalList[position].name

            }
        })
    }

    private fun initializeDoctorSpinner(
        doctorNameList: ArrayList<String>
    ) {
        doctorSpinner = SpinnerDialog(activity!!, doctorNameList, "") // With No Animation
        doctorSpinner.setCancellable(true) // for cancellable
        doctorSpinner.setShowKeyboard(false) // for open keyboard by default
        doctorSpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                doctorId = doctorlist[position].id
                updateDrNameSpinner.text = doctorlist[position].name
            }
        })
    }

    private fun initializeEtiologySpinner(
        EtiologyList: ArrayList<String>
    ) {
        etiologySpinner = SpinnerDialog(activity!!, EtiologyList, "") // With No Animation
        etiologySpinner.setCancellable(true) // for cancellable
        etiologySpinner.setShowKeyboard(false) // for open keyboard by default
        etiologySpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                etiology = etiologyList[position]
                updateEtiloigySpinner.text = etiologyList[position]
            }
        })
    }

}