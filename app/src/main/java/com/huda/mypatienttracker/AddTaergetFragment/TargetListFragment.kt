package com.huda.mypatienttracker.AddTaergetFragment

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Adapters.TargetAdapter
import com.huda.mypatienttracker.Models.TargetData
import com.huda.mypatienttracker.Models.TargetRequestModel
import com.huda.mypatienttracker.Models.updateTargetRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_target_fragment.*
import kotlinx.android.synthetic.main.add_target_fragment.view.*
import kotlinx.android.synthetic.main.update_target_fragment.*
import java.util.ArrayList


class TargetListFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addTargetFragmentViewModel: AddTargetFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var model: TargetRequestModel
    private lateinit var hospitalName: String
    private var hospitalSubmitTarget: Boolean = false
    private val modelFeedArrayList = arrayListOf<TargetData>()
    private lateinit var targetAdapter: TargetAdapter
    private lateinit var recyclerView: RecyclerView
    private var hospitalId: Int = 0
    private var targetYear: Int = -1
    private var targetMonth: Int = -1
    var mHasReachedBottomOnce = false
    private var fromType: String = ""
    private lateinit var selectedImage: Uri
    var currentPageNum = 1
    private var type = "All"
    var lastPageNum: Int = 0
    private val medicalList = arrayListOf<String>()
    private val monthList = arrayListOf<Int>()
    private val yearList = arrayListOf<Int>()
    private var flagSelected: Int = 0
    private lateinit var updateTargetBottomSheet: updateBottomSheet


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
        hospitalSubmitTarget = arguments?.getBoolean("hospitalSubmitTarget")!!
        setClickListeners()
        initRecyclerView()
        callTargetList("All", currentPageNum, false, false)
        callTotalTarget()
        intializeMedicalSpinner()
        intializeMonthSpinner()
        intializeYearSpinner()
    }

    private fun intializeMedicalSpinner() {
        medicalList.clear()
        medicalList.add("All")
        medicalList.add("com.huda.mypatienttracker.Models.HospitalModels.Opsumit")
        medicalList.add("com.huda.mypatienttracker.Models.HospitalModels.Uptravi")
        medicalList.add("com.huda.mypatienttracker.Models.HospitalModels.Tracleer")
        initializeTypeSpinner(Type, medicalList)
    }

    private fun intializeYearSpinner() {
        yearList.clear()
        yearList.add(2020)
        yearList.add(2021)
        yearList.add(2022)
        yearList.add(2023)
        yearList.add(2024)
        initializeYearSpinner(yearSpinner, yearList)
    }

    private fun intializeMonthSpinner() {
        monthList.clear()
        for (i in 1..12) {
            monthList.add(i)
        }
        initializeMonthSpinner(monthSpinner, monthList)
    }

    private fun initializeTypeSpinner(spinnerType: SearchableSpinner, typeList: ArrayList<String>) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    typeList
                )
            }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                hideKeyboard()
                val typeHospital = typeList[position]
                type = when (typeHospital) {
                    "All" -> {
                        flagSelected = 1
                        callTargetList("All", 1, false, false)
                        "All"
                    }
                    "com.huda.mypatienttracker.Models.HospitalModels.Opsumit" -> {
                        flagSelected = 1
                        callTargetList("opsumit", 1, false, false)
                        "opsumit"
                    }
                    "com.huda.mypatienttracker.Models.HospitalModels.Uptravi" -> {
                        flagSelected = 1
                        callTargetList("uptravi", 1, false, false)
                        "uptravi"
                    }
                    else -> {
                        flagSelected = 1
                        callTargetList("tracleer", 1, false, false)
                        "tracleer"
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinnerType.adapter = arrayAdapter
        }

    }

    private fun initializeYearSpinner(spinnerType: SearchableSpinner, typeList: ArrayList<Int>) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    typeList
                )
            }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                hideKeyboard()
                targetYear = yearList[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinnerType.adapter = arrayAdapter
        }
    }

    private fun initializeMonthSpinner(
        spinnerType: SearchableSpinner,
        typeList: ArrayList<Int>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    typeList
                )
            }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                hideKeyboard()
                targetMonth = monthList[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinnerType.adapter = arrayAdapter
        }
    }

    private fun setClickListeners() {
        submitTarget.setOnClickListener {
            submitTarget()
        }

        currentPageNum = 1
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        mainLayout.setOnClickListener {
            hideKeyboard()
        }


        type = "All"
        recyclerView = root.findViewById(R.id.targetRecycler)

        targetNumber.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard()
            }
        }


        AddTargetFragment.setOnClickListener {
            val number = targetNumber.text.toString().toIntOrNull()
            val isInteger = number != null
            if (targetNumber.text.toString().isEmpty() || targetYear == -1 || targetMonth == -1) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()

            } else if (type == "All") {
                Toast.makeText(
                    activity,
                    "Please Select Specific Type",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isInteger) {
                Toast.makeText(
                    activity,
                    "Please Enter com.huda.mypatienttracker.Models.HospitalModels.Target As Numbers",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                model = TargetRequestModel(
                    targetNumber.text.toString().toInt(),
                    targetYear,
                    targetMonth,
                    type
                )
                callSubmitTarget(hospitalId)
            }
        }
        root.mainLayout.setOnClickListener {
            hideKeyboard()
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

    }


    private fun callSubmitTarget(hospital: Int) {
        TargetProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addTargetFragmentViewModel.addTarget(hospital, model, accessToken)
        }
        addTargetFragmentViewModel.submitData().observe(this, Observer {
            TargetProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "error")
                    Toast.makeText(
                        activity,
                        it.type,
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()
                    targetNumber.text.clear()
                    callTargetList(type, 1, false, true)
                }
            } else {
                targetNumber.text.clear()
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteTarget(targetId: Int, position: Int) {
        TargetProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addTargetFragmentViewModel.deleteTarget(hospitalId, targetId, accessToken)
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

    private fun submitTarget() {
        totalTargetProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addTargetFragmentViewModel.submitTarget(hospitalId, accessToken)
        }
        addTargetFragmentViewModel.getsubmitData().observe(this, Observer {
            totalTargetProgressBar.visibility = View.GONE
            if (it != null) {
                hospitalSubmitTarget = true
                Toast.makeText(activity, "Target Submitted Successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        targetAdapter = TargetAdapter(hospitalName, modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = targetAdapter
        targetAdapter.setOnCommentListener(object : TargetAdapter.OnDotsClickListener {
            override fun onDotsImageClicked(position: Int, fromTab: String) {
                if (position != null) {
                    if (fromTab == "UpdateTarget") {
                        if (hospitalSubmitTarget) {
                            showAlertDialog("You Can't Update Target, Because You Confirmed Targets Before!")
                        } else {
                            updateTargetBottomSheet = updateBottomSheet()
                            fragmentManager?.let { it ->
                                updateTargetBottomSheet.show(
                                    it,
                                    "DeleteBottomSheet"
                                )
                            }
                            updateTargetBottomSheet.setOnAttendAddedListener(object :
                                updateBottomSheet.AttendanceListener {
                                override fun onTargetUpdated(number: Int) {
                                    callupdateTarget(
                                        position,
                                        modelFeedArrayList[position].id,
                                        number
                                    )
                                }


                            })
                        }
                    } else if (fromTab == "Delete") {
                        if (hospitalSubmitTarget) {
                            showAlertDialog("You Can't Delete Target, Because You Confirmed Targets Before!")
                        } else {
                            val targetId = modelFeedArrayList[position].id
                            deleteTarget(targetId, position)
                        }
                    }
                }
            }

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callTargetList(type, currentPageNum, true, false)

                    }
                }
            }
        })

    }

    private fun callTargetList(
        myType: String,
        page: Int,
        fromLoadMore: Boolean,
        fromRefresh: Boolean
    ) {
        if (fromLoadMore) {
            LoadMoreTargetProgressBar.visibility = View.VISIBLE
        } else {
            TargetProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            if (myType == "All") {
                addTargetFragmentViewModel.getAllTarget(page, hospitalId, accessToken)
            } else {
                addTargetFragmentViewModel.getTarget(myType, hospitalId, accessToken)
            }
        }
        addTargetFragmentViewModel.getTargetData().observe(this, Observer {
            if (fromLoadMore) {
                LoadMoreTargetProgressBar.visibility = View.GONE
            } else {
                modelFeedArrayList.clear()
                TargetProgressBar.visibility = View.GONE
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

    private fun callTotalTarget() {
        totalTargetProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addTargetFragmentViewModel.getTotalTarget(hospitalId, accessToken)
        }
        addTargetFragmentViewModel.getTargetTotalData().observe(this, Observer {
            totalTargetProgressBar.visibility = View.GONE
            if (it != null) {
                totalTarget.text = it.target.total.toString()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callupdateTarget(position: Int, target: Int, number: Int) {
        TargetProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            val model = updateTargetRequestModel(
                modelFeedArrayList[position].product,
                number,
                modelFeedArrayList[position].year,
                modelFeedArrayList[position].month,
                "put"
            )
            addTargetFragmentViewModel.updateTarget(hospitalId, target, model, accessToken)
        }
        addTargetFragmentViewModel.updateData().observe(this, Observer {
            TargetProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "error")
                    Toast.makeText(
                        activity,
                        it.type,
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Updated Successfully", Toast.LENGTH_SHORT).show()
                    callTargetList(type, 1, false, false)
                }
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

    private fun showAlertDialog(text: String) {
        val alertDialog = AlertDialog.Builder(activity!!, R.style.DialogTheme).create()
        alertDialog.setMessage(text)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}