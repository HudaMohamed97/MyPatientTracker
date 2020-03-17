package com.huda.mypatienttracker.AddReferal

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
import com.huda.mypatienttracker.Models.Cities
import com.huda.mypatienttracker.Models.CountryData
import com.huda.mypatienttracker.Models.Doctors
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_patient_fragment.doctorSpinner
import kotlinx.android.synthetic.main.add_referal.*


class AddReferalFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addReferalFragmentViewModel: AddReferalFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val doctorist = arrayListOf<Doctors>()
    private val doctorNameList = arrayListOf<String>()
    private var hospitalId: Int = -1
    private var doctorId: Int = -1
    private val countryList = arrayListOf<CountryData>()
    private val countriesNameList = arrayListOf<String>()
    private val cityList = arrayListOf<Cities>()
    private val citiesNameList = arrayListOf<String>()
    private var city_id: Int = -1
    private var country_id: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_referal, container, false)
        addReferalFragmentViewModel =
            ViewModelProviders.of(this).get(AddReferalFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        callHospitals("referal", false, false)
        getCountryList()
        callHospitals("coe", false, true)
    }

    private fun setClickListeners() {
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    }

    private fun getCountryList() {
        countryList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addReferalFragmentViewModel.getCountries(accessToken)
        }
        addReferalFragmentViewModel.getCountriesData().observe(this, Observer {
            if (it != null) {
                for (country in it.data) {
                    countryList.add(country)
                }
                prepareCountryList(countryList)
            }
        })
    }

    private fun prepareCountryList(countryList: ArrayList<CountryData>) {
        countriesNameList.clear()
        for (country in countryList) {
            countriesNameList.add(country.name)
        }
        initializeCountrySpinner(referalCountryspinner, countriesNameList)

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
        cityList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            cityProgressBar.visibility = View.VISIBLE
            addReferalFragmentViewModel.getCities(countryId, accessToken)
        }
        addReferalFragmentViewModel.getCitiesData().observe(this, Observer {
            if (it != null) {
                cityProgressBar.visibility = View.GONE
                for (city in it.data.cities) {
                    cityList.add(city)
                }
                prepareCityList(cityList)
            }
        })
    }

    private fun prepareCityList(cityList: ArrayList<Cities>) {
        citiesNameList.clear()
        for (city in cityList) {
            citiesNameList.add(city.name)
        }
        initializeCitySpinner(cityReferalSpinner, citiesNameList)
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

    private fun callHospitals(
        type: String,
        fromLoadMore: Boolean,
        fromCoe: Boolean
    ) {
        if (fromCoe) {
        } else {
            referalProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addReferalFragmentViewModel.getHospitals(type, accessToken)
        }
        addReferalFragmentViewModel.getData().observe(this, Observer {
            if (fromCoe) {
            } else {
                hospitalList.clear()
                referalProgressBar.visibility = View.GONE
            }
            if (it != null) {
                hospitalList.clear()
                for (data in it.data) {
                    hospitalList.add(data)
                }
                if (fromCoe) {
                    preparCoeHospitalList(hospitalList)

                } else {
                    preparHospitalList(hospitalList)
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun preparCoeHospitalList(hospitalList: ArrayList<HospitalData>) {

    }

    private fun callSingelHospital(
        hospitalId: Int,
        fromLoadMore: Boolean
    ) {
        doctorsProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addReferalFragmentViewModel.getSingelHospital(hospitalId, accessToken)
        }
        addReferalFragmentViewModel.getSingelData().observe(this, Observer {
            if (it != null) {
                doctorsProgressBar.visibility = View.GONE
                doctorSpinner.visibility = view?.visibility!!
                doctorist.clear()
                for (data in it.data.doctors) {
                    doctorist.add(data)
                }
                preparDoctorsList(doctorist)
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun preparDoctorsList(doctorist: ArrayList<Doctors>) {
        doctorNameList.clear()
        for (hospital in doctorist) {
            doctorNameList.add(hospital.name)
        }
        initializeDoctorSpinner(drReferalNameSpinner, doctorNameList)
    }


    private fun preparHospitalList(hopital_List: ArrayList<HospitalData>) {
        hospitalNameList.clear()
        for (hospital in hopital_List) {
            hospitalNameList.add(hospital.name)
        }
        initializeHospitalSpinner(hospitalReferalspinner, hospitalNameList)
    }

    private fun initializeHospitalSpinner(
        spinner: SearchableSpinner,
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

        hospitalReferalspinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    hospitalId = hospitalList[position].id
                    callSingelHospital(hospitalId, false)
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // your code here
                }
            }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            hospitalReferalspinner.adapter = arrayAdapter
        }

    }

    private fun initializeDoctorSpinner(
        spinner: SearchableSpinner,
        doctorsNameList: ArrayList<String>
    ) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    doctorsNameList
                )
            }

        drReferalNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                doctorId = doctorist[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            drReferalNameSpinner.adapter = arrayAdapter
        }

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