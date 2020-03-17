package com.huda.mypatienttracker.patientListFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Adapters.PatientAdapter
import com.huda.mypatienttracker.Models.PatientResponseData
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.patient_fragment_list.*


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
            patientProgressBar.visibility = View.VISIBLE
        } else {
            patientProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            patientaListViewModel.getPatients("no update", "referal", accessToken)
        }
        patientaListViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                patientProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                patientProgressBar.visibility = View.GONE
            }
            if (fromRefresh) {
                currentPageNum = 1
                modelFeedArrayList.clear()
            }
            if (it != null) {
                lastPageNum = it.meta.last_page
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
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
                /* if (fromTab == "AddDoctor") {
                     findNavController().navigate(R.id.action_HospitalListFragment_to_addDoctor)

                 } else if (fromTab == "") {
                     Toast.makeText(activity, "Please Select Action.", Toast.LENGTH_SHORT).show()

                 }*/

            }


        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        // callPosts(currentPageNum, true, false)

                    }
                }
            }
        })

    }

    private fun setClickListeners() {
        add_ref.setOnClickListener {
            findNavController().navigate(R.id.action_PatientList_addReferalFragment)
        }
        addNew.setOnClickListener {
            findNavController().navigate(R.id.action_PatientList_addPatientFragment)
        }
        recyclerView = root.findViewById(R.id.patientRecycler)
        val logOutButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }


    }


}