package com.huda.mypatienttracker.patientListFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Adapters.PatientAdapter
import com.huda.mypatienttracker.Models.DoctorDate
import com.huda.mypatienttracker.Models.PatientResponseData
import com.huda.mypatienttracker.Models.updatePatientRequestModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.patient_fragment_list.*
import kotlinx.android.synthetic.main.update_patient_fragment.*


class PatientaListFragment : Fragment() {
    private lateinit var root: View
    private lateinit var patientaListViewModel: PatientaListViewModel
    private val modelFeedArrayList = arrayListOf<PatientResponseData>()
    private lateinit var patientAdapter: PatientAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var lastPageNum: Int = 0


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


}