package com.huda.mypatienttracker.ReferalFragment

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
import com.huda.mypatienttracker.AddHospitalFragment.AddHospitalViewModel
import com.huda.mypatienttracker.Models.HospitalModels.updateHospitalRequestModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.update_hospital_peferal.*

class UpdateReferalFragment : Fragment() {
    private lateinit var root: View
    private lateinit var referalFragmentViewModel: AddHospitalViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var add_hospitalRequestModel: updateHospitalRequestModel
    private var echo: Int = 1
    private var attentive: Int = 1
    private var hospitalId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.update_hospital_peferal, container, false)
        referalFragmentViewModel = ViewModelProviders.of(this).get(AddHospitalViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_hospitalRequestModel = arguments?.getParcelable("Hospital")!!
        hospitalId = arguments?.getInt("hospitalId")!!
        rheuma.setText(add_hospitalRequestModel.rheuma.toString())
        cardio.setText(add_hospitalRequestModel.crdio.toString())
        pulmo.setText(add_hospitalRequestModel.pulmo.toString())
        setClickListeners()
    }

    private fun setClickListeners() {
        val radioECHO = root.findViewById(R.id.radioECHO) as RadioGroup
        radioECHO.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioYes -> {
                    echo = 1
                }
                R.id.radioNo -> {
                    echo = 0
                }

            }
        }

        val radioaTtentive = root.findViewById(R.id.radioaTTENTIVE) as RadioGroup
        radioaTtentive.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio12 -> {
                    attentive = 1
                }
                R.id.radio22 -> {
                    attentive = 0
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
                add_hospitalRequestModel.pah_expert = echo
                add_hospitalRequestModel.echo = echo
                add_hospitalRequestModel.pah_attentive = attentive
                add_hospitalRequestModel.put = "put"
                callUpdateHospital(hospitalId, add_hospitalRequestModel)

            }
        }

    }

    private fun callUpdateHospital(hospitalId: Int, model: updateHospitalRequestModel) {
        referalHospitalProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            referalFragmentViewModel.updateHospitals(hospitalId, model, accessToken)
        }
        referalFragmentViewModel.updateData().observe(this, Observer {
            referalHospitalProgressBar.visibility = View.GONE
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