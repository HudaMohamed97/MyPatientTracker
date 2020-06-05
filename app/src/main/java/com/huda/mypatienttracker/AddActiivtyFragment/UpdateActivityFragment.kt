package com.huda.mypatienttracker.AddActiivtyFragment

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
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
import com.huda.mypatienttracker.ActivityFragment.AddActivityViewModel
import com.huda.mypatienttracker.Models.*
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.activity_fragment_list.*
import kotlinx.android.synthetic.main.update_activity_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import java.util.Arrays.copyOf
import java.util.EnumSet.copyOf


class UpdateActivityFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addActivityViewModel: AddActivityViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var spinnerType: SpinnerDialog
    private lateinit var subTypespinner: SpinnerDialog
    private lateinit var typeSpinner: SpinnerDialog
    private lateinit var seakerspinner: SpinnerDialog
    private lateinit var citySpinner: SpinnerDialog
    private lateinit var localSpeakersSpinner: SpinnerDialog
    private lateinit var specialitySpinner: SpinnerDialog
    private lateinit var countrySpinner: SpinnerDialog
    private val medicalList = arrayListOf<String>()
    private val productList = arrayListOf<String>()
    private val specialityList = arrayListOf<String>()
    private val medicalSubList = arrayListOf<String>()
    private val speakerRequestList = arrayListOf<SpeakerRequestModel>()
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
    private var activityId: Int = 0
    private var selectedType: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.update_activity_fragment, container, false)
        addActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityId = arguments?.getInt("ActivityId")!!
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        callSingelActivity()
        intializeMedicalSpinner()
        intializeSpeakerSpinner()
        intializeSpecialitySpinner()
        intializeProductSpinner()
        setClickListeners()
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
                activity_Country_Spinner.text = countriesNameList[position]
                callCitiesPerCountry(country_id)
            }
        })
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

    private fun callSingelActivity() {
        updateActivtyProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addActivityViewModel.getSingelActivity(activityId, accessToken)
        }
        addActivityViewModel.getSingelData().observe(this, Observer {
            updateActivtyProgressBar.visibility = View.GONE
            if (it != null) {
                val type = it.data.type
                when (type) {
                    1 -> {
                        selectedType = 1
                        medicalUpdateSpinner.text = "MedicalEducation"
                    }
                    2 -> {
                        selectedType = 2
                        medicalUpdateSpinner.text = "MarketAccess"
                    }
                    else -> {
                        selectedType = 3
                        medicalUpdateSpinner.text = "Commercial"
                    }
                }
                subTypeSpinner.text = it.data.subtype
                Subtype = it.data.subtype
                date = it.data.date
                city_id = it.data.city.id
                productType = it.data.product
                datePicker.setText(it.data.date)
                ActivityProductType.text = it.data.product
                activity_City_Spinner.text = it.data.city.name
                speakersLocalSpinner.text = ""
                for (speaker in it.data.speakers) {
                    speakersLocalSpinner.append(speaker.name + " ")
                }
                speakerRequestList.addAll(it.data.speakers)
                specialityRequestedList.addAll(it.data.speciality)
                attandanceList.addAll(it.data.no_attendees)

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun updateActivity(
        speakers: HashMap<String, String>,
        body: AddActivityRequestModel, speciality: HashMap<String, String>,
        no_attendees: HashMap<String, String>
    ) {
        cityList.clear()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            activtyProgressBar.visibility = View.VISIBLE
            addActivityViewModel.updateActivity(
                activityId,
                speakers,
                body,
                speciality,
                no_attendees,
                accessToken
            )
        }
        addActivityViewModel.getupdateActivityData().observe(this, Observer {
            activtyProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Submitted Successfully", Toast.LENGTH_SHORT).show()
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
        initializeCitySpinner(citiesNameList)
    }

    private fun prepareDoctorList(doctorList: ArrayList<DoctorDate>) {
        doctorNameList.clear()
        for (doctor in doctorList) {
            doctorNameList.add(doctor.name)
        }
        initializeDoctorSpinner(doctorNameList)
    }


    private fun intializeSpeakerSpinner() {
        speakerList.clear()
        speakerList.add("Inter")
        speakerList.add("Local")
    }

    private fun intializeMedicalSpinner() {
        medicalList.clear()
        medicalList.add("MedicalEducation")
        medicalList.add("MarketAccess")
        medicalList.add("Commercial")
    }

    private fun intializeProductSpinner() {
        productList.clear()
        productList.add("opsumit")
        productList.add("uptravi")
        productList.add("tracleer")
    }

    private fun intializeSpecialitySpinner() {
        specialityList.clear()
        specialityList.add("PH")
        specialityList.add("RH")
        specialityList.add("Cardio")
        specialityList.add("Pharmacist")
        initializeSpecialitySpinner(specialityList)
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
                activity_City_Spinner.text = citiesNameList[position]
            }
        })

    }

    private fun initializeDoctorSpinner(
        doctorsNameList: ArrayList<String>
    ) {
        localSpeakersSpinner = SpinnerDialog(activity!!, doctorsNameList, "") // With No Animation
        localSpeakersSpinner.setCancellable(true) // for cancellable
        localSpeakersSpinner.setShowKeyboard(false) // for open keyboard by default
        localSpeakersSpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                speakersLocalSpinner.append(doctorsNameList[position])
                val speakerRequestModel = SpeakerRequestModel(
                    "Local",
                    DoctorList[position].type,
                    DoctorList[position].speciality,
                    DoctorList[position].name
                )
                speakerRequestList.add(speakerRequestModel)

            }
        })
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
        initializeSubTypeSpinner(medicalSubList)


    }

    private fun intializeMarketSubSpinner() {
        medicalSubList.clear()
        medicalSubList.add("AV Action Awareness")
        medicalSubList.add("AV Action Networking")
        medicalSubList.add("Speaker Event (Inside Hospital)")
        medicalSubList.add("Speaker Event (Outside Hospital)")
        medicalSubList.add("Others")
        initializeSubTypeSpinner(medicalSubList)


    }

    private fun intializCommercialSubSpinner() {
        medicalSubList.clear()
        medicalSubList.add("AV Action Awareness")
        medicalSubList.add("AV Action Networking")
        medicalSubList.add("Speaker Event (Inside Hospital)")
        medicalSubList.add("Speaker Event (Outside Hospital)")
        medicalSubList.add("Others")
        initializeSubTypeSpinner(medicalSubList)
    }


    private fun initMedicalSpinner(medicalList: ArrayList<String>) {
        spinnerType = SpinnerDialog(activity!!, medicalList, "") // With No Animation
        spinnerType.setCancellable(true) // for cancellable
        spinnerType.setShowKeyboard(false) // for open keyboard by default
        spinnerType.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                val typeHospital = medicalList[position]
                medicalUpdateSpinner.text = typeHospital
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
        })
    }

    private fun initialiProductSpinner(
        productTypeList: ArrayList<String>
    ) {
        typeSpinner = SpinnerDialog(activity!!, productTypeList, "") // With No Animation
        typeSpinner.setCancellable(true) // for cancellable
        typeSpinner.setShowKeyboard(false) // for open keyboard by default
        typeSpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                ActivityProductType.text = productTypeList[position]
                productType = productTypeList[position]
            }
        })
    }

    private fun initializeSpecialitySpinner(
        typeList: ArrayList<String>
    ) {
        specialitySpinner = SpinnerDialog(activity!!, typeList, "") // With No Animation
        specialitySpinner.setCancellable(true) // for cancellable
        specialitySpinner.setShowKeyboard(false) // for open keyboard by default
        specialitySpinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                val specialityText = typeList[position]
                specialityRequestedList.add(specialityText)
                SpecialitySpinner.text = specialityText
                showNumOfAttend()
            }
        })
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

    private fun initializeSubTypeSpinner(typeList: ArrayList<String>) {
        subTypespinner = SpinnerDialog(activity!!, typeList, "") // With No Animation
        subTypespinner.setCancellable(true) // for cancellable
        subTypespinner.setShowKeyboard(false) // for open keyboard by default
        subTypespinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                subTypeSpinner.text = typeList[position]
                Subtype = typeList[position]
            }
        })


    }


    private fun initializeSpeakerSpinner(
        typeList: ArrayList<String>
    ) {
        seakerspinner = SpinnerDialog(activity!!, typeList, "") // With No Animation
        seakerspinner.setCancellable(true) // for cancellable
        seakerspinner.setShowKeyboard(false) // for open keyboard by default
        seakerspinner.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String?, position: Int) {
                val typeSpeaker = typeList[position]
                speakerType = when (typeSpeaker) {
                    "Inter" -> {
                        flagSelected = 1
                        showInterLayout()
                        "Inter"
                    }
                    else -> {
                        flagSelected = 1
                        showLocalLayout()
                        "Local"
                    }

                }
            }
        })
    }

    private fun showInterLayout() {
        localSpeaker.visibility = View.VISIBLE
        customBottomSheet = CustomBottomSheet()
        customBottomSheet.setOnSpeakerAddedListener(object :
            CustomBottomSheet.OnSpeakerAddedListener {
            override fun onSpeakerAdded(speakerRequestModel: SpeakerRequestModel) {
                speakersLocalSpinner.append(" " + speakerRequestModel.name)
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
        medicalUpdateSpinner.setOnClickListener {
            if (medicalList.size != 0) {
                initMedicalSpinner(medicalList)
                spinnerType.showSpinerDialog()
            }
        }
        ActivityProductType.setOnClickListener {
            if (productList.size != 0) {
                initialiProductSpinner(productList)
                typeSpinner.showSpinerDialog()
            }
        }
        SpecialitySpinner.setOnClickListener {
            if (specialityList.size != 0) {
                specialitySpinner.showSpinerDialog()
            }
        }
        speaker_Spinner.setOnClickListener {
            if (speakerList.size != 0) {
                initializeSpeakerSpinner(speakerList)
                seakerspinner.showSpinerDialog()
            }
        }
        subTypeSpinner.setOnClickListener {
            if (medicalSubList.size != 0) {
                subTypespinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please Choose Medical First", Toast.LENGTH_SHORT).show()

            }
        }
        activity_City_Spinner.setOnClickListener {
            if (citiesNameList.size != 0) {
                citySpinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please Choose Country First", Toast.LENGTH_SHORT).show()

            }
        }
        activity_Country_Spinner.setOnClickListener {
            if (countriesNameList.size != 0) {
                countrySpinner.showSpinerDialog()
            } else {
                Toast.makeText(activity, "Please Wait", Toast.LENGTH_SHORT).show()

            }
        }
        speakersLocalSpinner.setOnClickListener {
            if (doctorNameList.size != 0) {
                localSpeakersSpinner.showSpinerDialog()
            } else {
                Toast.makeText(
                    activity,
                    "Please Select Type Of speaker First..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        updateActivityButtton.setOnClickListener {
            if (selectedType == -1 || city_id == -1 || specialityRequestedList.size == 0 || attandanceList.size == 0 || Subtype == "" ||
                speakerRequestList.size == 0 || date == "" || productType == ""
            ) {
                Toast.makeText(activity, "Please Add All fields Thanks", Toast.LENGTH_SHORT)
                    .show()
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
                    val type =
                        RequestBody.create(MediaType.parse("text/plain"), orderitems.type)
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
                updateActivity(speakers, body, specialitList, no_attendees)
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
        val logOutButton = root.findViewById(R.id.backButton) as ImageView
        logOutButton.setOnClickListener {
            findNavController().navigateUp()
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