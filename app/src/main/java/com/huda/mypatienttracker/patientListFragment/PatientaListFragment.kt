package com.huda.mypatienttracker.patientListFragment

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Adapters.PatientAdapter
import com.huda.mypatienttracker.Models.DoctorDate
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.huda.mypatienttracker.Models.PatientResponseData
import com.huda.mypatienttracker.Models.updatePatientRequestModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.add_activity_fragment.*
import kotlinx.android.synthetic.main.add_patient_fragment.*
import kotlinx.android.synthetic.main.hospital_fragment_list.*
import kotlinx.android.synthetic.main.patient_fragment_list.*
import kotlinx.android.synthetic.main.patient_fragment_list.patientProgressBar
import kotlinx.android.synthetic.main.update_hospital_fragment.*
import kotlinx.android.synthetic.main.update_patient_fragment.*


class PatientaListFragment : Fragment() {
    private lateinit var root: View
    private lateinit var patientaListViewModel: PatientaListViewModel
    private val modelFeedArrayList = arrayListOf<PatientResponseData>()
    private lateinit var patientAdapter: PatientAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var doctorSpinner: SpinnerDialog
    private lateinit var hospitalSpinner: SpinnerDialog
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var hospitalId = -1
    var doctorId = -1
    var lastPageNum: Int = 0
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val doctorList = arrayListOf<DoctorDate>()
    private val doctorNameList = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.patient_fragment_list, container, false)
        patientaListViewModel = ViewModelProviders.of(this).get(PatientaListViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        callPatients(1, false, false)
        callHospitals()
        callDoctors()
    }


    private fun callDoctors() {
        doctorList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            patientaListViewModel.getDoctors(accessToken)
        }
        patientaListViewModel.getDoctorsData().observe(this, Observer {
            if (it != null) {
                for (doctor in it.data) {
                    doctorList.add(doctor)
                }
                prepareDoctorList(doctorList)
            }
        })
    }

    private fun prepareDoctorList(doctorList: ArrayList<DoctorDate>) {
        doctorNameList.clear()
        for (doctor in doctorList) {
            doctorNameList.add(doctor.name)
        }
        initializeDoctorSpinner(doctorNameList)
    }


    private fun callPatients(page: Int, fromLoadMore: Boolean, fromRefresh: Boolean) {
        if (fromLoadMore) {
            LoadMorepatientProgressBar.visibility = View.VISIBLE
        } else {
            patientProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            patientaListViewModel.getPatients(page, accessToken)
        }
        patientaListViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                LoadMorepatientProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                patientProgressBar.visibility = View.GONE
            }
            if (fromRefresh) {
                currentPageNum = 1
                modelFeedArrayList.clear()
            }
            if (it != null) {
                currentPageNum = it.meta.current_page
                lastPageNum = it.meta.last_page
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    /* modelFeedArrayList.add(
                         PatientResponseData(
                             1,
                             "name",
                             "noUpdate",
                             false,
                             DoctorDate(0, "doc", "", ""),
                             null, ""
                         )
                     )
                     patientAdapter.notifyDataSetChanged()*/
                    Toast.makeText(activity, "No Patient Added Yet.", Toast.LENGTH_SHORT).show()

                }
                patientAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callPatientsByHospital(/*page: Int, fromLoadMore: Boolean, fromRefresh: Boolean*/) {
        /* if (fromLoadMore) {
             LoadMorepatientProgressBar.visibility = View.VISIBLE
         } else {*/
        patientProgressBar.visibility = View.VISIBLE
        // }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            patientaListViewModel.getPatientsByHospital(hospitalId, accessToken)
        }
        patientaListViewModel.getHospitalPatientData().observe(this, Observer {
            /* if (fromLoadMore) {
                 LoadMorepatientProgressBar.visibility = View.GONE
             } else {*/
            modelFeedArrayList.clear()
            patientProgressBar.visibility = View.GONE
            //}
            /* if (fromRefresh) {
                 currentPageNum = 1
                 modelFeedArrayList.clear()
             }*/
            if (it != null) {
                /*   currentPageNum = it.meta.current_page
                   lastPageNum = it.meta.last_page*/
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    /* modelFeedArrayList.add(
                         PatientResponseData(
                             1,
                             "name",
                             "noUpdate",
                             false,
                             DoctorDate(0, "doc", "", ""),
                             null, ""
                         )
                     )
                     patientAdapter.notifyDataSetChanged()*/
                    showAlertDialog("No Patient Added Yet.")

                }
                patientAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                // currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callPatientsByDoctor() {
        /* if (fromLoadMore) {
             LoadMorepatientProgressBar.visibility = View.VISIBLE
         } else {*/
        patientProgressBar.visibility = View.VISIBLE
        // }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            patientaListViewModel.getPatientsByDoctor(doctorId, accessToken)
        }
        patientaListViewModel.getdoctorPatientData().observe(this, Observer {
            /* if (fromLoadMore) {
                 LoadMorepatientProgressBar.visibility = View.GONE
             } else {*/
            modelFeedArrayList.clear()
            patientProgressBar.visibility = View.GONE
            //}
            /* if (fromRefresh) {
                 currentPageNum = 1
                 modelFeedArrayList.clear()
             }*/
            if (it != null) {
                /*   currentPageNum = it.meta.current_page
                   lastPageNum = it.meta.last_page*/
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    showAlertDialog("No Patient Added Yet.")

                }
                patientAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                // currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initializeHospitalSpinner(
        countriesNameList: ArrayList<String>
    ) {
        hospitalSpinner = SpinnerDialog(activity!!, countriesNameList, "") // With No Animation
        hospitalSpinner.setCancellable(true) // for cancellable
        hospitalSpinner.setShowKeyboard(false) // for open keyboard by default
        hospitalSpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                hospitalId = hospitalList[position].id
                hospital_spinner.text = hospitalList[position].name
                doctor_Spinner.text = "choose Doctor "
                callPatientsByHospital(/*1,false,false*/)
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
                doctorId = doctorList[position].id
                doctor_Spinner.text = doctorList[position].name
                hospital_spinner.text = "choose Hospital "
                callPatientsByDoctor(/*1,false,false*/)
            }
        })
    }

    private fun callHospitals() {

        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            patientaListViewModel.getHospitals(accessToken)
        }
        patientaListViewModel.getHospitalsData().observe(this, Observer {
            modelFeedArrayList.clear()
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
        initializeHospitalSpinner(hospitalNameList)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        patientAdapter = PatientAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = patientAdapter
        patientAdapter.setOnCommentListener(object : PatientAdapter.OnCommentClickListener {
            override fun onDotsImageClicked(position: Int, fromTab: String) {
                if (fromTab == "Confirmed") {
                    callStatuesPatient(
                        "confirmed",
                        position,
                        modelFeedArrayList[position].id,
                        "confirmed"
                    )


                    /* val bundle = Bundle()
                     bundle.putInt("PatientId", modelFeedArrayList[position].id)
                     bundle.putInt("HospitalId", modelFeedArrayList[position].hospital?.id!!)
                    // bundle.putInt("HospitalId", 1)
                    // bundle.putInt("DoctorId", 1)
                     bundle.putString("HospitalName", modelFeedArrayList[position].hospital?.name)
                     bundle.putInt("DoctorId", modelFeedArrayList[position].doctor.id)
                     //bundle.putString("HospitalName", "name")
                     findNavController().navigate(
                         R.id.action_PatientList_updateReferalPatientFragment,
                         bundle
                     )*/

                } else if (fromTab == "") {
                    Toast.makeText(activity, "Please Select Action.", Toast.LENGTH_SHORT).show()

                } else if (fromTab == "notPh") {
                    callStatuesPatient(
                        "not Ph",
                        position,
                        modelFeedArrayList[position].id,
                        "not ph"
                    )
                }

            }


        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callPatients(currentPageNum, true, false)
                    }
                }
            }
        })

    }

    private fun callStatuesPatient(from: String, position: Int, PatientId: Int, statues: String) {
        patientProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            patientaListViewModel.updateStatuesPatient(PatientId, statues, accessToken)
        }
        patientaListViewModel.getUpdateStatuesPatient().observe(this, Observer {
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
                    if (from == "not Ph") {
                        modelFeedArrayList[position].status = "not Ph"
                        patientAdapter.notifyItemChanged(position);
                    } else {
                        modelFeedArrayList.removeAt(position)
                        //callPatients(1,false,false)
                        patientAdapter.notifyDataSetChanged()
                    }

                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun setClickListeners() {
        currentPageNum = 1
        lastPageNum = 0
        hospital_spinner.setOnClickListener {
            if (hospitalNameList.size != 0) {
                hospitalSpinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please Wait", Toast.LENGTH_SHORT).show()

            }
        }
        doctor_Spinner.setOnClickListener {
            if (doctorNameList.size != 0) {
                doctorSpinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please Wait", Toast.LENGTH_SHORT).show()

            }
        }
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        add_ref.setOnClickListener {
            findNavController().navigate(R.id.action_PatientList_addReferalFragment)
        }
        addNew.setOnClickListener {
            findNavController().navigate(R.id.action_PatientList_addPatientFragment)
        }
        updatePatient.setOnClickListener {
            findNavController().navigate(R.id.action_PatientList_CoePatientFragment)
        }
        recyclerView = root.findViewById(R.id.patientRecycler)


    }

    private fun showAlertDialog(text: String) {
        val alertDialog = AlertDialog.Builder(activity!!, R.style.DialogTheme).create()
        alertDialog.setMessage(text)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}