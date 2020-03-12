package com.huda.mypatienttracker.AddDoctorFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.huda.mypatienttracker.Models.AddDoctorModel
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.add_doctor.*
import kotlinx.android.synthetic.main.login_fragment.*

class AddDoctorFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addFragmentViewModel: AddFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_doctor, container, false)
        addFragmentViewModel = ViewModelProviders.of(this).get(AddFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        addDoctorButton.setOnClickListener {

           val  model=AddDoctorModel("doctor","doctor","expert",1)
            callAddDoctor(model)
        }

        mainView.setOnClickListener {
            hideKeyboard()
        }


    }

    private fun callAddDoctor(model: AddDoctorModel) {
        addDoctorProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            addFragmentViewModel.addDoctor(model, accessToken)
        }
        addFragmentViewModel.submitData().observe(this, Observer {
            addDoctorProgressBar.visibility = View.GONE
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