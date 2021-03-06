package com.huda.mypatienttracker.ActivityFragment

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
import com.huda.mypatienttracker.Adapters.ActivityAdapter
import com.huda.mypatienttracker.Models.ActivityData
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.activity_fragment_list.*


class ActivityFragment : Fragment() {
    private lateinit var root: View
    private lateinit var activityViewModel: AddActivityViewModel
    private val modelFeedArrayList = arrayListOf<ActivityData>()
    private lateinit var activityAdapter: ActivityAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    private var type: Int = -1
    var mHasReachedBottomOnce = false
    var fromRefresh = false
    var currentPageNum = 1
    private var fromType: String = ""
    var lastPageNum: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.activity_fragment_list, container, false)
        activityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        if (!fromRefresh) {
            currentActivity.isChecked = true
            callActivity(1, false, false)
        }
        radioActivity.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.currentActivity -> {
                    fromType = "currentActivity"
                    callActivity(1, false, false)
                }
                R.id.PreviousActivity -> {
                    fromType = "PreviousActivity"
                    callPreviousActivity(1, false, false)
                }
            }
        }

    }

    private fun callActivity(page: Int, fromLoadMore: Boolean, fromRefresh: Boolean) {
        if (fromLoadMore) {
            LoadMoreActivityListProgressBar.visibility = View.VISIBLE
        } else {
            ActivityProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            activityViewModel.getActivity(page, accessToken)
        }
        activityViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                LoadMoreActivityListProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                ActivityProgressBar.visibility = View.GONE
            }
            if (it != null) {
                currentPageNum = it.meta.current_page
                lastPageNum = it.meta.last_page
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Activity Added Yet.", Toast.LENGTH_SHORT).show()
                }
                activityAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callPreviousActivity(page: Int, fromLoadMore: Boolean, fromRefresh: Boolean) {
        if (fromLoadMore) {
            LoadMoreActivityListProgressBar.visibility = View.VISIBLE
        } else {
            ActivityProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            activityViewModel.getPreviousActivity(page, accessToken)
        }
        activityViewModel.getPrviousActivityData().observe(this, Observer {
            if (fromLoadMore) {
                LoadMoreActivityListProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                ActivityProgressBar.visibility = View.GONE
            }
            if (it != null) {
                currentPageNum = it.meta.current_page
                lastPageNum = it.meta.last_page
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Activity Added Yet.", Toast.LENGTH_SHORT).show()
                }
                activityAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        activityAdapter = ActivityAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = activityAdapter
        activityAdapter.setOnCommentListener(object : ActivityAdapter.OnCommentClickListener {
            override fun onDotsImageClicked(position: Int, fromTab: String) {
                if (fromTab == "Delete") {
                    callDeletActivity(modelFeedArrayList[position].id, position)
                } else if (fromTab == "") {
                    Toast.makeText(activity, "Please Select Action.", Toast.LENGTH_SHORT).show()
                } else if (fromTab == "Update") {
                    val bundle = Bundle()
                    bundle.putInt("ActivityId", modelFeedArrayList[position].id)
                    fromRefresh = true
                    findNavController().navigate(R.id.action_Activity_to_Update_Activity, bundle)
                }

            }


        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        if (fromType == "PreviousActivity") {
                            callPreviousActivity(currentPageNum, true, false)
                        } else {
                            callActivity(currentPageNum, true, false)
                        }

                    }
                }
            }
        })

    }

    private fun callDeletActivity(id: Int, position: Int) {
        ActivityProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            activityViewModel.deleteActivity(id, accessToken)
        }
        activityViewModel.getAactivityDeletedData().observe(this, Observer {
            ActivityProgressBar.visibility = View.GONE
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
                    activityAdapter.notifyDataSetChanged()
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
        modelFeedArrayList.clear()
        add_Activity_layout.setOnClickListener {
            findNavController().navigate(R.id.action_Activity_to_Add_Activity)
        }
        recyclerView = root.findViewById(R.id.hospitalRecycler)
    }


}