package com.huda.mypatienttracker.AddReferal

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.Models.Cities
import com.huda.mypatienttracker.Models.CountryData
import com.huda.mypatienttracker.Models.Doctors
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.huda.mypatienttracker.Models.HospitalModels.PatientReferalRequestModel
import com.huda.mypatienttracker.Models.PatientRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_patient_fragment.*
import kotlinx.android.synthetic.main.add_patient_fragment.doctorSpinner
import kotlinx.android.synthetic.main.add_referal.*
import kotlinx.android.synthetic.main.add_target_fragment.*


class AddReferalFragment : Fragment() {
    private lateinit var root: View
    private var submitTarget: Boolean = false
    private lateinit var addReferalFragmentViewModel: AddReferalFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val hospitalList = arrayListOf<HospitalData>()
    private val hospitalNameList = arrayListOf<String>()
    private val hospitalCeoList = arrayListOf<HospitalData>()
    private val hospitalCeoNameList = arrayListOf<String>()
    private val doctorist = arrayListOf<Doctors>()
    private val doctorNameList = arrayListOf<String>()
    private val toDoctorist = arrayListOf<Doctors>()
    private val toDoctorNameList = arrayListOf<String>()
    private var hospitalId: Int = -1
    private var toHospitalId: Int = -1
    private var doctorId: Int = -1
    private var toDoctorId: Int = -1
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
        callHospitals("coe", false, false)
        getCountryList()
    }

    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        hospitalId = -1
        toHospitalId = -1
        doctorId = -1
        toDoctorId = -1
        city_id = -1
        country_id = -1
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        submitReferalButton.setOnClickListener {
            if (hospitalId == -1 || doctorId == -1 || toDoctorId == -1 || toHospitalId == -1 || city_id == -1 || country_id == -1) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()

            } else {
                val model = PatientReferalRequestModel(
                    "name",
                    city_id,
                    country_id,
                    hospitalId,
                    doctorId,
                    toHospitalId,
                    toDoctorId
                )
                callAddPatient(model)
            }
        }


    }

    private fun callAddPatient(model: PatientReferalRequestModel) {
        referalProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addReferalFragmentViewModel.submitPatient(model, accessToken)
        }
        addReferalFragmentViewModel.getSubmitPatient().observe(this, Observer {
            referalProgressBar.visibility = View.GONE
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
                hospitalCeoList.clear()
            } else {
                hospitalList.clear()
                referalProgressBar.visibility = View.GONE
            }
            if (it != null) {
                for (data in it.data) {
                    if (fromCoe) {
                        hospitalCeoList.add(data)
                    } else {
                        hospitalList.add(data)
                    }
                }
                if (fromCoe) {
                    preparCoeHospitalList(hospitalCeoList)
                } else {
                    preparHospitalList(hospitalList)
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun preparCoeHospitalList(hospitalList: ArrayList<HospitalData>) {
        hospitalCeoNameList.clear()
        for (hospital in hospitalList) {
            hospitalCeoNameList.add(hospital.name)
        }
        initializeCeoHospitalSpinner(ToHospitalspinner, hospitalCeoNameList)

    }

    private fun callSingelHospital(
        hospitalId: Int,
        fromLoadMore: Boolean,
        toHospital: Boolean
    ) {
        if (toHospital) {
            toDoctorsProgressBar.visibility = View.VISIBLE
        } else {
            doctorsProgressBar.visibility = View.VISIBLE
        }
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addReferalFragmentViewModel.getSingelHospital(hospitalId, accessToken)
        }
        addReferalFragmentViewModel.getSingelData().observe(this, Observer {
            if (it != null) {
                submitTarget = it.data.submit_target
                if (toHospital) {
                    toDoctorsProgressBar.visibility = View.GONE
                } else {
                    doctorsProgressBar.visibility = View.GONE
                }
                doctorSpinner.visibility = view?.visibility!!
                if (toHospital) {
                    toDoctorist.clear()
                } else {
                    doctorist.clear()
                }
                for (data in it.data.doctors) {
                    if (toHospital) {
                        toDoctorist.add(data)
                    } else {
                        doctorist.add(data)
                    }
                }
                if (toHospital) {
                    preparToDoctorsList(toDoctorist)
                } else {
                    preparDoctorsList(doctorist)
                }
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

    private fun preparToDoctorsList(doctorist: ArrayList<Doctors>) {
        toDoctorNameList.clear()
        for (hospital in doctorist) {
            toDoctorNameList.add(hospital.name)
        }
        initializeToDoctorSpinner(ToDoctorspinner, toDoctorNameList)
    }


    private fun preparHospitalList(hopital_List: ArrayList<HospitalData>) {
        callHospitals("coe", false, true)
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
                    callSingelHospital(hospitalId, false, false)
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

    private fun initializeCeoHospitalSpinner(
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

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    toHospitalId = hospitalCeoList[position].id
                    callSingelHospital(toHospitalId, false, true)

                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // your code here
                }
            }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinner.adapter = arrayAdapter
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

    private fun initializeToDoctorSpinner(
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                toDoctorId = toDoctorist[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinner.adapter = arrayAdapter
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