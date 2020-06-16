package com.huda.mypatienttracker.CeoFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.AddHospitalFragment.AddHospitalViewModel
import com.huda.mypatienttracker.Models.HospitalModels.updateHospitalRequestModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.update_hospital_coe.*

class UpdateCeoFragment : Fragment() {
    private lateinit var root: View
    private lateinit var ceoFragmentViewModel: AddHospitalViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var add_hospitalRequestModel: updateHospitalRequestModel
    private var pah: Int = 1
    private var rhc: Int = 1
    private var rwe: Int = 1
    private var hospitalId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.update_hospital_coe, container, false)
        ceoFragmentViewModel = ViewModelProviders.of(this).get(AddHospitalViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hospitalId = arguments?.getInt("hospitalId")!!
        add_hospitalRequestModel = arguments?.getParcelable("Hospital")!!
        setClickListeners()
    }

    private fun setClickListeners() {

        val radioPAH = root.findViewById(R.id.radioPAH) as RadioGroup
        radioPAH.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.yesRadio -> {
                    pah = 1
                }
                R.id.noRadio -> {
                    pah = 0
                }

            }
        }

        val radiorhc = root.findViewById(R.id.radiorhc) as RadioGroup
        radiorhc.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio2 -> {
                    rhc = 1
                }
                R.id.radio22 -> {
                    rhc = 0
                }

            }
        }
        val radioRwe = root.findViewById(R.id.radioRwe) as RadioGroup
        radioRwe.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio31 -> {
                    rwe = 1
                }
                R.id.radio32 -> {
                    rwe = 0
                }

            }
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        submitButton.setOnClickListener {
            if (rheuma.text.isEmpty() || cardio.text.isEmpty() || pulmo.text.isEmpty()) {
                Toast.makeText(activity, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            } else {
                add_hospitalRequestModel.rheuma = rheuma.text.toString().toInt()
                add_hospitalRequestModel.crdio = cardio.text.toString().toInt()
                add_hospitalRequestModel.pulmo = pulmo.text.toString().toInt()
                add_hospitalRequestModel.pah_expert = pah
                add_hospitalRequestModel.rhc = rhc
                add_hospitalRequestModel.rwe = rwe
                add_hospitalRequestModel.put = "put"
                updateHospital(hospitalId, add_hospitalRequestModel)

            }
        }
        backButton.setOnClickListener{
            findNavController().navigateUp()
        }

    }

    private fun updateHospital(hospitalId: Int, model: updateHospitalRequestModel) {
        ceoHospitalProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            ceoFragmentViewModel.updateHospitals(hospitalId, model, accessToken)
        }
        ceoFragmentViewModel.updateData().observe(this, Observer {
            ceoHospitalProgressBar.visibility = View.GONE
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


    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}