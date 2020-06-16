package com.huda.mypatienttracker.CoePatientLIst

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Adapters.CoePatientAdapter
import com.huda.mypatienttracker.Models.DoctorDate
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.huda.mypatienttracker.Models.PatientResponseData
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.coe_patient_list_fragment.*
import kotlinx.android.synthetic.main.coe_patient_list_fragment.doctor_Spinner
import kotlinx.android.synthetic.main.coe_patient_list_fragment.hospital_spinner
import kotlinx.android.synthetic.main.patient_fragment_list.*

class CeoPatientListFragment : Fragment() {
    private lateinit var root: View
    private lateinit var ceoPatientaListViewModel: CeoPatientaListViewModel
    private val modelFeedArrayList = arrayListOf<PatientResponseData>()
    private lateinit var patientAdapter: CoePatientAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var lastPageNum: Int = 0
    var hospitalId = -1
    var doctorId = -1
    private lateinit var doctorSpinner: SpinnerDialog
    private lateinit var hospitalSpinner: SpinnerDialog
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val doctorList = arrayListOf<DoctorDate>()
    private val doctorNameList = arrayListOf<String>()
    private var fromSearchFlag: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.coe_patient_list_fragment, container, false)
        ceoPatientaListViewModel =
            ViewModelProviders.of(this).get(CeoPatientaListViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        callPatients(1, false)
        callHospitals()
        callDoctors()
    }

    private fun callPatients(page: Int, fromLoadMore: Boolean) {
        fromSearchFlag = false
        if (fromLoadMore) {
            coeLoadMoreProgressBar.visibility = View.VISIBLE
        } else {
            coePatientProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            ceoPatientaListViewModel.getPatients(page, "confirmed", accessToken)
        }
        ceoPatientaListViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                coeLoadMoreProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                coePatientProgressBar.visibility = View.GONE
            }
            if (it != null) {
                lastPageNum = it.meta.last_page
                currentPageNum = it.meta.current_page
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Patient Added Yet.", Toast.LENGTH_SHORT).show()

                }
                recyclerView.recycledViewPool.clear()
                patientAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        patientAdapter = CoePatientAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = patientAdapter
        patientAdapter.setOnCommentListener(object : CoePatientAdapter.OnCommentClickListener {
            override fun onDotsImageClicked(position: Int, fromTab: String) {
                if (fromTab == "update") {
                    val bundle = Bundle()
                    bundle.putParcelable("PatientModel", modelFeedArrayList[position])
                    findNavController().navigate(R.id.action_navigate_to_update, bundle)
                } else if (fromTab == "") {
                    Toast.makeText(activity, "Please Select Action.", Toast.LENGTH_SHORT).show()

                }
            }


        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce&&!fromSearchFlag) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callPatients(currentPageNum, true)

                    }
                }
            }
        })

    }

    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
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
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        recyclerView = root.findViewById(R.id.patientRecycler)


    }

    private fun callPatientsByHospital(/*page: Int, fromLoadMore: Boolean, fromRefresh: Boolean*/) {
        fromSearchFlag = true
        /* if (fromLoadMore) {
             LoadMorepatientProgressBar.visibility = View.VISIBLE
         } else {*/
        coePatientProgressBar.visibility = View.VISIBLE
        // }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            ceoPatientaListViewModel.getPatientsByHospital(hospitalId, accessToken)
        }
        ceoPatientaListViewModel.getHospitalPatientData().observe(this, Observer {
            /* if (fromLoadMore) {
                 LoadMorepatientProgressBar.visibility = View.GONE
             } else {*/
            modelFeedArrayList.clear()
            coePatientProgressBar.visibility = View.GONE
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
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callPatientsByDoctor() {
        fromSearchFlag = true
        /* if (fromLoadMore) {
             LoadMorepatientProgressBar.visibility = View.VISIBLE
         } else {*/
        coePatientProgressBar.visibility = View.VISIBLE
        // }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            ceoPatientaListViewModel.getPatientsByDoctor(doctorId, accessToken)
        }
        ceoPatientaListViewModel.getdoctorPatientData().observe(this, Observer {
            /* if (fromLoadMore) {
                 LoadMorepatientProgressBar.visibility = View.GONE
             } else {*/
            modelFeedArrayList.clear()
            coePatientProgressBar.visibility = View.GONE
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

    private fun callDoctors() {
        doctorList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            ceoPatientaListViewModel.getDoctors(accessToken)
        }
        ceoPatientaListViewModel.getDoctorsData().observe(this, Observer {
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
            ceoPatientaListViewModel.getHospitals(accessToken)
        }
        ceoPatientaListViewModel.getHospitalsData().observe(this, Observer {
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

    private fun showAlertDialog(text: String) {
        val alertDialog = AlertDialog.Builder(activity!!, R.style.DialogTheme).create()
        alertDialog.setMessage(text)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}