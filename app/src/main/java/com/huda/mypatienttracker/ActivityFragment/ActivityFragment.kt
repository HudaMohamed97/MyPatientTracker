package com.huda.mypatienttracker.ActivityFragment

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
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
    var currentPageNum = 1
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
        callActivity(1, false, false)

    }

    private fun callActivity(page: Int, fromLoadMore: Boolean, fromRefresh: Boolean) {
        if (fromLoadMore) {
            ActivityProgressBar.visibility = View.VISIBLE
        } else {
            ActivityProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            activityViewModel.getActivity(accessToken)
        }
        activityViewModel.getData().observe(this, Observer {
            if (fromLoadMore) {
                ActivityProgressBar.visibility = View.GONE
            } else {
                ActivityProgressBar.visibility = View.GONE
            }
            if (fromRefresh) {
                currentPageNum = 1
                modelFeedArrayList.clear()
            }
            if (it != null) {
                lastPageNum = it.meta.last_page
                for (data in it.data) {
                    modelFeedArrayList.clear()
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Posts Added Yet.", Toast.LENGTH_SHORT).show()

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
                if (fromTab == "AddActivity") {
                    findNavController().navigate(R.id.action_Activity_to_Add_Activity)

                } else if (fromTab == "") {
                    Toast.makeText(activity, "Please Select Action.", Toast.LENGTH_SHORT).show()

                }

            }


        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        //  callPosts(currentPageNum, true, false)

                    }
                }
            }
        })

    }

    private fun setClickListeners() {
        add_Activity_layout.setOnClickListener {
            findNavController().navigate(R.id.action_Activity_to_Add_Activity)
        }
        recyclerView = root.findViewById(R.id.hospitalRecycler)
        val logOutButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            activity!!.finish()
        }


    }


}