package com.huda.mypatienttracker.HospitalList

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
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
import com.huda.mypatienttracker.R
import com.huda.mypatienttracker.Adapters.HospitalAdapter
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import kotlinx.android.synthetic.main.hospital_fragment_list.*


class HospitalFragment : Fragment() {
    private lateinit var root: View
    private lateinit var hospitalViewModel: HospitalViewModel
    private val modelFeedArrayList = arrayListOf<HospitalData>()
    private lateinit var hospitalAdapter: HospitalAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    private var type: Int = -1
    var mHasReachedBottomOnce = false
    private var fromType: String = ""
    private lateinit var selectedImage: Uri
    var currentPageNum = 1
    var lastPageNum: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.hospital_fragment_list, container, false)
        hospitalViewModel = ViewModelProviders.of(this).get(HospitalViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        val rg = root.findViewById(R.id.radioHospital) as RadioGroup
        callHospitals("coe", 1, false, false)
        rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.coe -> {
                    fromType = "coe"
                    callHospitals("coe", 1, false, false)
                }
                R.id.referal -> {
                    fromType = "referal"
                    callHospitals("referal", 1, false, false)
                }

            }
        }

    }

    private fun callHospitals(
        type: String,
        page: Int,
        fromLoadMore: Boolean,
        fromRefresh: Boolean
    ) {
        if (fromLoadMore) {
            hospitalProgressBar.visibility = View.VISIBLE
        } else {
            hospitalProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            hospitalViewModel.getHospitals(type, accessToken)
        }
        hospitalViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                hospitalProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                hospitalProgressBar.visibility = View.GONE
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
                    Toast.makeText(activity, "No Hospitals Added Yet.", Toast.LENGTH_SHORT).show()

                }
                hospitalAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        hospitalAdapter = HospitalAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = hospitalAdapter
        hospitalAdapter.setOnCommentListener(object : HospitalAdapter.OnDotsClickListener {
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
                    val bundle = Bundle()
                    bundle.putInt("hospitalId", hospitalId)
                    bundle.putString("hospitalName", hospitalName)
                    findNavController().navigate(
                        R.id.action_HospitalListFragment_to_targetFragment,
                        bundle
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
                        callHospitals("", currentPageNum, true, false)

                    }
                }
            }
        })

    }


    private fun deleteHospital(hospitalId: Int, position: Int, type: String) {
        hospitalProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            hospitalViewModel.deleteHospitals(hospitalId, accessToken)
        }
        hospitalViewModel.getDeleteData().observe(this, Observer {
            hospitalProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.type == "error")
                    Toast.makeText(
                        activity,
                        it.title,
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                    modelFeedArrayList.removeAt(position)
                    hospitalAdapter.notifyDataSetChanged()
                    //callHospitals(type, 1, false, false)
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setClickListeners() {
        addHospital.setOnClickListener {
            findNavController().navigate(R.id.action_HospitalListFragment_to_addHospital)

        }
        recyclerView = root.findViewById(R.id.hospitalRecycler)
        val logOutButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }


    }


}