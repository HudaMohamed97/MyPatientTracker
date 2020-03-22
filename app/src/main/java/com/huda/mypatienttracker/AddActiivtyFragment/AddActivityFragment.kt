package com.huda.mypatienttracker.AddActiivtyFragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.ActivityFragment.AddActivityViewModel
import com.huda.mypatienttracker.Models.*
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_activity_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddActivityFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addActivityViewModel: AddActivityViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var spinnerType: SearchableSpinner
    private lateinit var seakerType: SearchableSpinner
    private lateinit var citySpinner: SearchableSpinner
    private val medicalList = arrayListOf<String>()
    private val productList = arrayListOf<String>()
    private val specialityList = arrayListOf<String>()
    private val medicalSubList = arrayListOf<String>()
    private val speakerRequestList = arrayListOf<SpeakerRequestModel>()
    private val commercialList = arrayListOf<String>()
    private val marketList = arrayListOf<String>()
    private val attandanceList = arrayListOf<String>()
    private val specialityRequestedList = arrayListOf<String>()
    private val speakerList = arrayListOf<String>()
    private var type = ""
    private var productType = ""
    private var specialityText = ""
    private var Subtype = ""
    private var date = ""
    private lateinit var customBottomSheet: CustomBottomSheet
    private lateinit var attendBottomSheet: AttendBottomSheet
    private var speakerType = ""
    private val countryList = arrayListOf<CountryData>()
    private val countriesNameList = arrayListOf<String>()
    private var city_id: Int = -1
    private var country_id: Int = -1
    private val cityList = arrayListOf<Cities>()
    private val DoctorList = arrayListOf<DoctorDate>()
    private val citiesNameList = arrayListOf<String>()
    private val doctorNameList = arrayListOf<String>()
    private var flagSelected: Int = 0
    private var selectedType: Int = -1


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
        intializeSpecialitySpinner()
        intializeProductSpinner()
        getCountryList()
    }

    private fun getCountryList() {
        countryList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addActivityViewModel.getCountries(accessToken)
        }
        addActivityViewModel.getCountriesData().observe(this, Observer {
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
        initializeCountrySpinner(activityCountrySpinner, countriesNameList)

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
            activtyCityProgressBar.visibility = View.VISIBLE
            addActivityViewModel.getCities(countryId, accessToken)
        }
        addActivityViewModel.getCitiesData().observe(this, Observer {
            if (it != null) {
                activtyCityProgressBar.visibility = View.GONE
                for (city in it.data.cities) {
                    cityList.add(city)
                }
                prepareCityList(cityList)
            }
        })
    }

    private fun callDoctors() {
        DoctorList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            speakerProgressBar.visibility = View.VISIBLE
            addActivityViewModel.getDoctors(accessToken)
        }
        addActivityViewModel.getDoctorData().observe(this, Observer {
            if (it != null) {
                speakerProgressBar.visibility = View.GONE
                for (doctor in it.data) {
                    DoctorList.add(doctor)
                }
                prepareDoctorList(DoctorList)
            }
        })
    }

    private fun addActivity(
        speakers: HashMap<String, String>,
        body: AddActivityRequestModel, speciality: HashMap<String, String>,
        no_attendees: HashMap<String, String>
    ) {
        cityList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            activtyProgressBar.visibility = View.VISIBLE
            addActivityViewModel.addActivity(speakers, body, speciality, no_attendees, accessToken)
        }
        addActivityViewModel.getAddActivityData().observe(this, Observer {
            activtyProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()

                /*if (it.type == "error")
                    Toast.makeText(
                        activity,
                        it.title,
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()
                }*/
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun prepareCityList(cityList: ArrayList<Cities>) {
        citiesNameList.clear()
        for (city in cityList) {
            citiesNameList.add(city.name)
        }
        initializeCitySpinner(activityCitySpinner, citiesNameList)
    }

    private fun prepareDoctorList(doctorList: ArrayList<DoctorDate>) {
        doctorNameList.clear()
        for (doctor in doctorList) {
            doctorNameList.add(doctor.name)
        }
        initializeDoctorSpinner(speakersSpinner, doctorNameList)
    }


    private fun intializeSpeakerSpinner() {
        speakerList.clear()
        speakerList.add("Inter")
        speakerList.add("Local")
        initializeSpeakerSpinner(speakerSpinner, speakerList)
    }

    private fun intializeMedicalSpinner() {
        medicalList.clear()
        medicalList.add("MedicalEducation")
        medicalList.add("MarketAccess")
        medicalList.add("Commercial")
        initializeTypeSpinner(spinnerType, medicalList)
    }

    private fun intializeProductSpinner() {
        productList.clear()
        productList.add("opsumit")
        productList.add("uptravi")
        productList.add("tracleer")
        initialiProductSpinner(ActivityProductTypespinner, productList)
    }

    private fun intializeSpecialitySpinner() {
        specialityList.clear()
        specialityList.add("PH")
        specialityList.add("RH")
        specialityList.add("Cardio")
        specialityList.add("Pharmacist")
        initializeSpecialitySpinner(Specialityspinner, specialityList)
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

    private fun initializeDoctorSpinner(
        spinner: SearchableSpinner,
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
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val speakerRequestModel = SpeakerRequestModel(
                    "Local",
                    DoctorList[position].type,
                    DoctorList[position].speciality,
                    DoctorList[position].name
                )
                speakerRequestList.add(speakerRequestModel)

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


    private fun intializeMedicalSubSpinner() {
        medicalSubList.clear()
        medicalSubList.add("Satellite Symposium")
        medicalSubList.add("Sponsorship Local/Regional")
        medicalSubList.add("Sponsorship International")
        medicalSubList.add("Speaker Event (Inside Hospital)")
        medicalSubList.add("Speaker Event (Outside Hospital)")
        medicalSubList.add("Standalone")
        medicalSubList.add("Webinar")
        medicalSubList.add("Others")
        initializeSubTypeSpinner(subTypespinner, medicalSubList)


    }

    private fun intializeMarketSubSpinner() {
        marketList.clear()
        marketList.add("AV Action Awareness")
        marketList.add("AV Action Networking")
        marketList.add("Speaker Event (Inside Hospital)")
        marketList.add("Speaker Event (Outside Hospital)")
        marketList.add("Others")
        initializeSubTypeSpinner(subTypespinner, medicalList)


    }

    private fun intializCommercialSubSpinner() {
        commercialList.clear()
        commercialList.add("AV Action Awareness")
        initializeTypeSpinner(subTypespinner, commercialList)
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
                        selectedType = 1
                        flagSelected = 1
                        intializeMedicalSubSpinner()
                        "MedicalEducation"

                    }
                    "MarketAccess" -> {
                        selectedType = 2
                        flagSelected = 1
                        intializeMarketSubSpinner()
                        "MarketAccess"
                    }
                    else -> {
                        selectedType = 3
                        flagSelected = 1
                        intializCommercialSubSpinner()
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

    private fun initialiProductSpinner(
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

                productType = productList[position]

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

    private fun initializeSpecialitySpinner(
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
                val specialityText = typeList[position]
                specialityRequestedList.add(specialityText)
                showNumOfAttend()

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

    private fun showNumOfAttend() {
        attendBottomSheet = AttendBottomSheet()
        attendBottomSheet.setOnAttendAddedListener(object : AttendBottomSheet.AttendanceListener {
            override fun onAttendedAdd(number: String) {
                attandanceList.add(number)
            }

        }

        )
        if (!attendBottomSheet.isAdded) {
            fragmentManager?.let {
                attendBottomSheet.show(
                    it,
                    ""
                )
            }
        }
    }

    private fun initializeSubTypeSpinner(spinner: SearchableSpinner, typeList: ArrayList<String>) {
        val arrayAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    typeList
                )
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                Subtype = typeList[position]
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

    private fun showInterLayout() {
        localSpeaker.visibility = View.GONE
        customBottomSheet = CustomBottomSheet()
        customBottomSheet.setOnSpeakerAddedListener(object :
            CustomBottomSheet.OnSpeakerAddedListener {
            override fun onSpeakerAdded(speakerRequestModel: SpeakerRequestModel) {
                speakerRequestList.add(speakerRequestModel)
            }


        })
        fragmentManager?.let {
            customBottomSheet.show(
                it,
                ""
            )
        }
    }


    private fun showLocalLayout() {
        callDoctors()
        localSpeaker.visibility = View.VISIBLE

    }


    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        addActivityButtton.setOnClickListener {
            if (selectedType == -1 || city_id == -1 || specialityRequestedList.size == 0 || attandanceList.size == 0 || Subtype == "" ||
                speakerRequestList.size == 0 || date == "" || productType == ""
            ) {
                Toast.makeText(activity, "Please Add All fields Thanks", Toast.LENGTH_SHORT).show()
            } else {
                val body = AddActivityRequestModel(
                    selectedType,
                    Subtype,
                    productType,
                    date,
                    speakerRequestList,
                    city_id
                )

                val speakers = HashMap<String, String>()
                for (i in 0 until speakerRequestList.size) {
                    val orderitems = speakerRequestList[i]
                    /*  val name = RequestBody.create(MediaType.parse("text/plain"), orderitems.name)
                      val speaker_type =
                          RequestBody.create(MediaType.parse("text/plain"), orderitems.speaker_type)
                      val speciality =
                          RequestBody.create(MediaType.parse("text/plain"), orderitems.speciality)*/
                    val type = RequestBody.create(MediaType.parse("text/plain"), orderitems.type)

                    speakers["speakers[${i}][name]"] = (orderitems.name)
                    speakers["speakers[${i}][speaker_type]"] = (orderitems.speaker_type)
                    speakers["speakers[${i}][speciality]"] = (orderitems.speciality)
                    speakers["speakers[${i}][type]"] = (orderitems.type)
                }
                val specialitList = HashMap<String, String>()
                for (i in 0 until specialityRequestedList.size) {
                    val orderitems = RequestBody.create(
                        MediaType.parse("text/plain"),
                        specialityRequestedList[i]
                    )
                    specialitList["speciality[${i}]"] = specialityRequestedList[i]

                }
                val no_attendees = HashMap<String, String>()
                for (i in 0 until attandanceList.size) {
                    val orderitems = RequestBody.create(
                        MediaType.parse("text/plain"),
                        attandanceList[i]
                    )
                    no_attendees["no_attendees[${i}]"] = attandanceList[i]

                }
                addActivity(speakers, body, specialitList, no_attendees)
            }
        }

        speakerRequestList.clear()
        addSpeaker.setOnClickListener {
            if (speakerType == "") {
                Toast.makeText(activity, "Please Choose Speaker Type First", Toast.LENGTH_SHORT)
                    .show()
            } else if (speakerType == "Inter") {
                showInterLayout()
            } else {
                showLocalLayout()
            }

        }

        activityList.setOnClickListener {
            findNavController().navigateUp()
        }
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
                    //  val myFormat = "dd-MM-yyyy" // mention the format you need
                    val myFormat = "yyyy-MM-dd" // mention the format you need
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    date = sdf.format(c.time)
                    datePicker.setText(sdf.format(c.time))
                },
                year,
                month,
                day
            )
            dpd.show()


        }
    }


}