package com.huda.mypatienttracker.AddPatientFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.login_fragment.*
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.add_patient_fragment.*


class AddPatientFragment : Fragment() {
    companion object {
        val UPTRAVI: String = "UPTRAVIE"
        val OPSUMIT: String = "OPSUMIT"
    }

    private lateinit var root: View
    private var fromType: String = ""
    private lateinit var addPatientFragmentViewModel: AddPatientFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_patient_fragment, container, false)
        addPatientFragmentViewModel =
            ViewModelProviders.of(this).get(AddPatientFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {

        val rg = root.findViewById(R.id.radioPatientGroup) as RadioGroup

        continueButton.setOnClickListener {
            if (fromType == UPTRAVI) {
                if (findNavController().currentDestination?.id == R.id.addPatientFragment) {
                    findNavController().navigate(R.id.action_PatientList_toUptravi)
                }

            } else if (fromType == OPSUMIT) {
                if (findNavController().currentDestination?.id == R.id.addPatientFragment) {
                    findNavController().navigate(R.id.action_PatientList_toOpsumit)

                } else if (fromType == "") {
                    Toast.makeText(activity, "Please Select Patient Type", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioUptarvi -> {
                    fromType = UPTRAVI
                }
                R.id.radioOpsumit -> {
                    fromType = OPSUMIT
                }

            }
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

    }

    private fun callLoginRequest() {
        progressBar.visibility = View.VISIBLE
        /* homeViewModel.login(
             email.text.toString(),
             passwordEt.text.toString()
         )*/
        /* loginViewModel.getData().observe(this, Observer {
             progressBar.visibility = View.GONE
             if (it != null) {
                 if (it.access_token != "") {
                     saveData(it)
                     saveUserData()
                     if (findNavController().currentDestination?.id == R.id.loginFragment) {
                         //   findNavController().navigate(R.id.action_LoginFragment_to_Home)
                     }
                 } else {
                     var error = it.token_type.replace("[", "")
                     error = error.replace("]", "")
                     Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                 }
             } else {
                 Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
             }


         })*/

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