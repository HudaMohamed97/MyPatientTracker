package com.huda.mypatienttracker.HospitalList

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Adapters.HospitalDetailsAdapter
import com.huda.mypatienttracker.Models.Doctors
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.hospital_details.*


class HospitalDetailsFragment : Fragment() {
    private lateinit var root: View
    private var submitTarget: Boolean = false
    private lateinit var hospitalViewModel: HospitalViewModel
    private val modelFeedArrayList = arrayListOf<Doctors>()
    private lateinit var hospitalAdapter: HospitalDetailsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    private var hospitalId: Int = -1
    var mHasReachedBottomOnce = false
    private var fromType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.hospital_details, container, false)
        hospitalViewModel = ViewModelProviders.of(this).get(HospitalViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        hospitalId = arguments?.getInt("hospitalId")!!
        setClickListeners()
        initRecyclerView()
        if (hospitalId != -1) {
            callHospitals(hospitalId)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun callHospitals(hospitalId: Int) {
        hospitalDetailsProgressBar.visibility = View.VISIBLE

        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            hospitalViewModel.getSingelHospitals(hospitalId, accessToken)
        }
        hospitalViewModel.getSingelHospitalData().observe(this, Observer {
            hospitalDetailsProgressBar.visibility = View.GONE
            if (it != null) {
                for (doctor in it.data.doctors) {
                    modelFeedArrayList.add(doctor)
                }
                PatientText.text =
                    "TotalPatients  " + it.data.patients_counts.total.toString() +
                            "  Uptravi " + it.data.patients_counts.uptravi.toString() + "  Opsumit   " +
                            it.data.patients_counts.opsumit.toString() + "  tracleer   " +
                            it.data.patients_counts.tracleer.toString()


                DoctorsText.text = "Doctors  " + it.data.doctors_count

                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Doctors Added Yet.", Toast.LENGTH_SHORT).show()

                }
                hospitalAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                //currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        hospitalAdapter = HospitalDetailsAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = hospitalAdapter
/*
        hospitalAdapter.setOnCommentListener(object : HospitalDetailsAdapter.OnDotsClickListener {
            override fun onDotsImageClicked(position: Int, fromTab: String) {
                if (fromTab == "AddDoctor") {
                    val hospitalId = modelFeedArrayList[position].id
                    val bundle = Bundle()
                    bundle.putInt("hospitalId", hospitalId)
                    findNavController().navigate(
                        R.id.action_HospitalListFragment_to_addDoctor,
                        bundle
                    )

                } else if (fromTab == "Delete") {
                    val hospitalId = modelFeedArrayList[position].id
                    deleteHospital(hospitalId, position, fromType)
                } else if (fromTab == "Target") {
                    val hospitalId = modelFeedArrayList[position].id
                    val hospitalName = modelFeedArrayList[position].name
                    val submitTarget = modelFeedArrayList[position].submit_target
                    val bundle = Bundle()
                    bundle.putInt("hospitalId", hospitalId)
                    bundle.putString("hospitalName", hospitalName)
                    bundle.putBoolean("hospitalSubmitTarget", submitTarget)
                    findNavController().navigate(
                        R.id.action_HospitalListFragment_to_targetFragment,
                        bundle
                    )

                } else if (fromTab == "Update") {
                    val hospitalId = modelFeedArrayList[position].id
                    val bundle = Bundle()
                    bundle.putParcelable("hospitalData", modelFeedArrayList[position])
                    findNavController().navigate(
                        R.id.action_HospitalListFragment_to_UpdateHospital,
                        bundle
                    )
                }
                else if (fromTab == "Details") {
                    val hospitalId = modelFeedArrayList[position].id
                    val bundle = Bundle()
                    bundle.putParcelable("hospitalData", modelFeedArrayList[position])
                    findNavController().navigate(
                        R.id.action_HospitalListFragment_to_UpdateHospital,
                        bundle
                    )
                }

            }


        })
*/

/*
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callHospitals(currentPageNum, true, false)

                    }
                }
            }
        })
*/

    }


    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        recyclerView = root.findViewById(R.id.hospitalRecycler)


    }


}