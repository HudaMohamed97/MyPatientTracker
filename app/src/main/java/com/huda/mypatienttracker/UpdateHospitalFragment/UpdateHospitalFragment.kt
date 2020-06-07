package com.huda.mypatienttracker.UpdateHospitalFragment

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
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
import com.huda.mypatienttracker.AddHospitalFragment.AddHospitalViewModel
import com.huda.mypatienttracker.Models.Cities
import com.huda.mypatienttracker.Models.CountryData
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData
import com.huda.mypatienttracker.Models.HospitalModels.updateHospitalRequestModel
import com.huda.mypatienttracker.Models.SpeakerRequestModel
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_doctor.mainView
import kotlinx.android.synthetic.main.update_activity_fragment.*
import kotlinx.android.synthetic.main.update_hospital_fragment.*

class UpdateHospitalFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addHospitalViewModel: AddHospitalViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var add_hospitalRequestModel: updateHospitalRequestModel
    private lateinit var spinnerType: SpinnerDialog
    private lateinit var countrySpinner: SpinnerDialog
    private lateinit var citySpinner: SpinnerDialog
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
    private lateinit var hospitalData: HospitalData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.update_hospital_fragment, container, false)
        addHospitalViewModel = ViewModelProviders.of(this).get(AddHospitalViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hospitalData = arguments?.getParcelable("hospitalData")!!
        setClickListeners()
        nameEditText.setText(" " + hospitalData.name)
        typeSpinner.text = hospitalData.type
        country_Spinner.text = hospitalData.country.name
        city_Spinner.text = hospitalData.city.name
        country_id = hospitalData.country.id
        type = hospitalData.type
        city_id = hospitalData.city.id
        flagSelected = 1
        prepareTypeList()
        getCountryList()
    }

    private fun prepareTypeList() {
        typeList.clear()
        typeList.add("COE")
        typeList.add("REFERAL")
        initializeTypeSpinner(typeList)

    }

    private fun getCountryList() {
        countryList.clear()
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
        countriesNameList.clear()
        for (country in countryList) {
            countriesNameList.add(country.name)
        }
        initializeCountrySpinner(countriesNameList)

    }

    private fun initializeCountrySpinner(
        countriesNameList: ArrayList<String>
    ) {
        countrySpinner = SpinnerDialog(activity!!, countriesNameList, "") // With No Animation
        countrySpinner.setCancellable(true) // for cancellable
        countrySpinner.setShowKeyboard(false) // for open keyboard by default
        countrySpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                country_id = countryList[position].id
                country_Spinner.text = countryList[position].name
                callCitiesPerCountry(country_id)
            }
        })
    }

    private fun callCitiesPerCountry(countryId: Int) {
        cityList.clear()
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
        citiesNameList.clear()
        for (city in cityList) {
            citiesNameList.add(city.name)
        }
        initializeCitySpinner(citiesNameList)
    }

    private fun initializeCitySpinner(
        citiesNameList: ArrayList<String>
    ) {
        citySpinner = SpinnerDialog(activity!!, citiesNameList, "") // With No Animation
        citySpinner.setCancellable(true) // for cancellable
        citySpinner.setShowKeyboard(false) // for open keyboard by default
        citySpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                city_id = cityList[position].id
                city_Spinner.text = cityList[position].name

            }
        })
    }


    private fun initializeTypeSpinner(typeList: ArrayList<String>) {
        spinnerType = SpinnerDialog(activity!!, typeList, "") // With No Animation
        spinnerType.setCancellable(true) // for cancellable
        spinnerType.setShowKeyboard(false) // for open keyboard by default
        spinnerType.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                val typeHospital = typeList[position]
                typeSpinner.text = typeHospital
                type = if (typeHospital == "COE") {
                    flagSelected = 1
                    "COE"

                } else {
                    flagSelected = 1
                    "referal"
                }

            }
        })


    }

    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }


        flagSelected = 0
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
                        add_hospitalRequestModel = updateHospitalRequestModel(
                            name,
                            type,
                            city_id.toInt(),
                            country_id.toInt(),
                            hospitalData.rheuma,
                            hospitalData.crdio,
                            hospitalData.pulmo,
                            hospitalData.pah_expert,
                            hospitalData.rhc,
                            hospitalData.rwe,
                            hospitalData.echo,
                            hospitalData.pah_attentive,
                            "put"
                        )
                        val bundle = Bundle()
                        bundle.putParcelable("Hospital", add_hospitalRequestModel)
                        bundle.putInt("hospitalId", hospitalData.id)

                        findNavController().navigate(R.id.action_updateHospital_coeFragment, bundle)
                    } else {
                        add_hospitalRequestModel = updateHospitalRequestModel(
                            name,
                            type,
                            city_id.toInt(),
                            country_id.toInt(),
                            hospitalData.rheuma,
                            hospitalData.crdio,
                            hospitalData.pulmo,
                            hospitalData.pah_expert,
                            hospitalData.rhc,
                            hospitalData.rwe,
                            hospitalData.echo,
                            hospitalData.pah_attentive,
                            "put"
                        )
                        val bundle = Bundle()
                        bundle.putParcelable("Hospital", add_hospitalRequestModel)
                        bundle.putInt("hospitalId", hospitalData.id)
                        findNavController().navigate(
                            R.id.action_updateHospital_ReferalFragment,
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