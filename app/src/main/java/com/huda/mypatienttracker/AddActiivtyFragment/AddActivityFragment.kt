package com.huda.mypatienttracker.AddActiivtyFragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.huda.mypatienttracker.ActivityFragment.AddActivityViewModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_activity_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class AddActivityFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addActivityViewModel: AddActivityViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var spinnerType: SearchableSpinner
    private lateinit var seakerType: SearchableSpinner
    private lateinit var citySpinner: SearchableSpinner
    private val medicalList = arrayListOf<String>()
    private val speakerList = arrayListOf<String>()
    private lateinit var type: String
    private lateinit var speakerType: String
    private var flagSelected: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_activity_fragment, container, false)
        addActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        intializeMedicalSpinner()
        intializeSpeakerSpinner()
    }

    private fun intializeSpeakerSpinner() {
        speakerList.clear()
        speakerList.add("Inter")
        speakerList.add("Local")
        initializeTypeSpinner(spinnerType, medicalList)
        initializeSpeakerSpinner(speakerSpinner, speakerList)
    }

    private fun intializeMedicalSpinner() {
        medicalList.clear()
        medicalList.add("MedicalEducation")
        medicalList.add("MarketAccess")
        medicalList.add("Commercial")
        initializeTypeSpinner(spinnerType, medicalList)


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

                val typeHospital = typeList[position]
                type = when (typeHospital) {
                    "MedicalEducation" -> {
                        flagSelected = 1
                        "MedicalEducation"

                    }
                    "MarketAccess" -> {
                        flagSelected = 1
                        "MarketAccess"
                    }
                    else -> {
                        flagSelected = 1
                        "Commercial"
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            medicalspinner.adapter = arrayAdapter
        }

    }

    private fun initializeSpeakerSpinner(
        spinnerType: SearchableSpinner,
        typeList: ArrayList<String>
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

                val typeHospital = typeList[position]
                speakerType = when (typeHospital) {
                    "Inter" -> {
                        flagSelected = 1
                        "Inter"

                    }
                    else -> {
                        flagSelected = 1
                        "Local"
                    }

                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            speakerSpinner.adapter = arrayAdapter
        }

    }


    private fun callActivity(page: Int, fromLoadMore: Boolean, fromRefresh: Boolean) {
        /*  if (fromLoadMore) {
              postLoadProgressBar.visibility = View.VISIBLE
          } else {
              PostsProgressBar.visibility = View.VISIBLE
          }
          val accessToken = loginPreferences.getString("accessToken", "")
          if (accessToken != null) {
              hospitalViewModel.getPosts(page, accessToken)
          }
          hospitalViewModel.getData().observe(this, Observer {
              if (fromLoadMore) {
                  postLoadProgressBar.visibility = View.GONE
              } else {
                  PostsProgressBar.visibility = View.GONE
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
                      Toast.makeText(activity, "No Posts Added Yet.", Toast.LENGTH_SHORT).show()

                  }
                  hospitalAdapter.notifyDataSetChanged()
                  mHasReachedBottomOnce = false
                  currentPageNum++

              } else {
                  Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
              }
          })*/
    }


    private fun setClickListeners() {

        val logOutButton = root.findViewById(R.id.backButton) as ImageView
        spinnerType = root.findViewById(R.id.medicalspinner)
        seakerType = root.findViewById(R.id.speakerSpinner)
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        datePicker.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                activity!!,
                R.style.DialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, monthOfYear)
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val myFormat = "dd.MM.yyyy" // mention the format you need
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    datePicker.setText(sdf.format(c.time))

                },
                year,
                month,
                day
            )
            dpd.show()


        }
    }

  /*  fun addActivity(
        model: AddActivityRequestModel,
        accessToken: String
    ): MutableLiveData<SubmitModel> {

    }*/


}