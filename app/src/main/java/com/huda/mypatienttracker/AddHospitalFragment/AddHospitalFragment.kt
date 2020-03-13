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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.Models.Cities
import com.huda.mypatienttracker.Models.CountriesResonse
import com.huda.mypatienttracker.Models.CountryData
import com.huda.mypatienttracker.Models.addHospitalRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_doctor.*
import kotlinx.android.synthetic.main.add_doctor.mainView
import kotlinx.android.synthetic.main.add_hospital_fragment.*
import kotlinx.android.synthetic.main.hospital_coe.*
import kotlinx.android.synthetic.main.login_fragment.*

class AddHospitalFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addHospitalViewModel: AddHospitalViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var add_hospitalRequestModel: addHospitalRequestModel
    private lateinit var spinnerType: SearchableSpinner
    private lateinit var countrySpinner: SearchableSpinner
    private lateinit var citySpinner: SearchableSpinner
    private lateinit var name: String
    private var city_id: Int = -1
    private var country_id: Int = -1
    private lateinit var type: String
    private var flagSelected: Int = 0
    private val countryList = arrayListOf<CountryData>()
    private val countriesNameList = arrayListOf<String>()
    private val cityList = arrayListOf<Cities>()
    private val citiesNameList = arrayListOf<String>()
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
        getCountryList()
    }

    private fun prepareTypeList() {
        typeList.clear()
        typeList.add("COE")
        typeList.add("REFERAL")
        initializeTypeSpinner(spinnerType, typeList)

    }

    private fun getCountryList() {
        hospitalProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addHospitalViewModel.getCountries(accessToken)
        }
        addHospitalViewModel.getCountriesData().observe(this, Observer {
            hospitalProgressBar.visibility = View.GONE
            if (it != null) {
                for (country in it.data) {
                    countryList.add(country)
                }
                prepareCountryList(countryList)
            }
        })


    }


    private fun prepareCountryList(countryList: ArrayList<CountryData>) {
        for (country in countryList) {
            countriesNameList.add(country.name)
        }
        initializeCountrySpinner(countrySpinner, countriesNameList)

    }

    private fun initializeCountrySpinner(
        countrySpinner: SearchableSpinner,
        countriesNameList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    countriesNameList
                )
            }

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                val cml = parentView.getItemAtPosition(position).toString()
                country_id = countryList[position].id
                callCitiesPerCountry(country_id)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }


        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            countrySpinner.adapter = arrayAdapter
        }

    }

    private fun callCitiesPerCountry(countryId: Int) {
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addHospitalViewModel.getCities(countryId, accessToken)
        }
        addHospitalViewModel.getCitiesData().observe(this, Observer {
            if (it != null) {
                for (city in it.data.cities) {
                    cityList.add(city)
                }
                prepareCityList(cityList)
            }
        })


    }

    private fun prepareCityList(cityList: ArrayList<Cities>) {
        for (city in cityList) {
            citiesNameList.add(city.name)
        }
        initializeCitySpinner(citySpinner, citiesNameList)
    }

    private fun initializeCitySpinner(
        citySpinner: SearchableSpinner,
        citiesNameList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    citiesNameList
                )
            }

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                city_id = cityList[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }


        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            citySpinner.adapter = arrayAdapter
        }

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
        countrySpinner = root.findViewById(R.id.countrySpinner)
        citySpinner = root.findViewById(R.id.citySpinner)
        mainView.setOnClickListener {
            hideKeyboard()
        }
        hospital_add_button.setOnClickListener {
            name = nameEditText.text.toString()
            if (city_id == -1 || name.isEmpty() || country_id == -1) {
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