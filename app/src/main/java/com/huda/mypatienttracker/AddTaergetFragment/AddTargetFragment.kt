package com.huda.mypatienttracker.AddTaergetFragment

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Adapters.TargetAdapter
import com.huda.mypatienttracker.Models.TargetData
import com.huda.mypatienttracker.Models.TargetRequestModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.add_target_fragment.*
import kotlinx.android.synthetic.main.add_target_fragment.view.*

class AddTargetFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addTargetFragmentViewModel: AddTargetFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var model: TargetRequestModel
    private lateinit var hospitalName: String
    private val modelFeedArrayList = arrayListOf<TargetData>()
    private lateinit var targetAdapter: TargetAdapter
    private lateinit var recyclerView: RecyclerView
    private var hospitalId: Int = 0
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
        root = inflater.inflate(R.layout.add_target_fragment, container, false)
        addTargetFragmentViewModel =
            ViewModelProviders.of(this).get(AddTargetFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hospitalId = arguments?.getInt("hospitalId")!!
        hospitalName = arguments?.getString("hospitalName")!!
        setClickListeners()
        initRecyclerView()
        callTargetList(1, false, false)
    }

    private fun setClickListeners() {
        recyclerView = root.findViewById(R.id.targetRecycler)

        AddTargetFragment.setOnClickListener {
            if (targetNumber.text.toString().isEmpty() || targetYear.text.toString().isEmpty() || targetMonth.text.toString().isEmpty()) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()

            } else if (targetMonth.text.toString().toInt() > 12) {
                Toast.makeText(
                    activity,
                    "Month should not be grater than 12 thanks",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                model = TargetRequestModel(
                    targetNumber.text.toString().toInt(),
                    targetYear.text.toString().toInt(),
                    targetMonth.text.toString().toInt(),
                    hospitalId
                )
                callSubmitTarget()
            }
        }
        HospitalName.text = hospitalName

        root.mainLayout.setOnClickListener {
            hideKeyboard()
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

    }

    private fun callSubmitTarget() {
        TargetProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addTargetFragmentViewModel.addTarget(model, accessToken)
        }
        addTargetFragmentViewModel.submitData().observe(this, Observer {
            TargetProgressBar.visibility = View.GONE
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

    private fun deleteTarget(targetId: Int, position: Int) {
        TargetProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addTargetFragmentViewModel.deleteTarget(targetId, accessToken)
        }
        addTargetFragmentViewModel.submitDeleteData().observe(this, Observer {
            TargetProgressBar.visibility = View.GONE
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
                    targetAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        targetAdapter = TargetAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = targetAdapter
        targetAdapter.setOnCommentListener(object : TargetAdapter.OnDotsClickListener {
            override fun onDotsImageClicked(position: Int, fromTab: String) {
                if (fromTab == "UpdateTarget") {
                    /* val hospitalId = modelFeedArrayList[position].id
                     val bundle = Bundle()
                     bundle.putInt("hospitalId", hospitalId)
                     findNavController().navigate(
                         R.id.action_HospitalListFragment_to_addDoctor,
                         bundle
                     )*/

                } else if (fromTab == "Delete") {
                    val targetId = modelFeedArrayList[position].id
                    deleteTarget(targetId, position)
                }
            }


        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        //callHospitals("", currentPageNum, true, false)

                    }
                }
            }
        })

    }

    private fun callTargetList(
        page: Int,
        fromLoadMore: Boolean,
        fromRefresh: Boolean
    ) {
        if (fromLoadMore) {
            TargetProgressBar.visibility = View.VISIBLE
        } else {
            TargetProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addTargetFragmentViewModel.getTarget(hospitalId, accessToken)
        }
        addTargetFragmentViewModel.getTargetData().observe(this, Observer {
            if (fromLoadMore) {
                TargetProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                TargetProgressBar.visibility = View.GONE
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
                targetAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
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