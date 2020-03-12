package com.huda.mypatienttracker.AddHospitalFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.Models.addHospitalRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_doctor.*
import kotlinx.android.synthetic.main.add_doctor.mainView
import kotlinx.android.synthetic.main.add_hospital_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*

class AddHospitalFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addHospitalViewModel: AddHospitalViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var add_hospitalRequestModel: addHospitalRequestModel
    private lateinit var spinnerType: SearchableSpinner
    private lateinit var name: String
    private lateinit var city_id: String
    private lateinit var country_id: String
    private lateinit var type: String
    private var flagSelected: Int = 0
    private val typeList = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_hospital_fragment, container, false)
        addHospitalViewModel =
            ViewModelProviders.of(this).get(AddHospitalViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        prepareTypeList()
    }

    private fun prepareTypeList() {
        typeList.clear()
        typeList.add("COE")
        typeList.add("REFERAL")
        initializeTypeSpinner(spinnerType, typeList)

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
                type = if (typeHospital == "COE") {
                    flagSelected = 1
                    "COE"

                } else {
                    flagSelected = 1
                    "referal"
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            typeSpinner.adapter = arrayAdapter
        }

    }

    private fun setClickListeners() {
        flagSelected = 0
        spinnerType = root.findViewById(R.id.typeSpinner)
        mainView.setOnClickListener {
            hideKeyboard()
        }
        hospital_add_button.setOnClickListener {
            city_id = cityEditText.text.toString()
            name = nameEditText.text.toString()
            country_id = countryEditText.text.toString()
            if (city_id.isEmpty() || name.isEmpty() || country_id.isEmpty()) {
                Toast.makeText(activity, "please fill All Fields", Toast.LENGTH_SHORT).show()
            } else {
                if (flagSelected == 0) {
                    Toast.makeText(activity, "please Choose Type", Toast.LENGTH_SHORT).show()

                } else {
                    if (type == "COE") {
                        add_hospitalRequestModel = addHospitalRequestModel(
                            name, type, city_id.toInt(), country_id.toInt(), 0, 0, 0, 0, 0,
                            0, 0, 0
                        )
                        val bundle = Bundle()
                        bundle.putParcelable("Hospital", add_hospitalRequestModel)
                        findNavController().navigate(R.id.action_addHospital_coeFragment, bundle)
                    } else {
                        add_hospitalRequestModel = addHospitalRequestModel(
                            name, type, city_id.toInt(), country_id.toInt(), 0, 0, 0, 0, 0,
                            0, 0, 0
                        )
                        val bundle = Bundle()
                        bundle.putParcelable("Hospital", add_hospitalRequestModel)
                        findNavController().navigate(
                            R.id.action_addHospital_ReferalFragment,
                            bundle
                        )
                    }
                }
            }
        }

        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

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