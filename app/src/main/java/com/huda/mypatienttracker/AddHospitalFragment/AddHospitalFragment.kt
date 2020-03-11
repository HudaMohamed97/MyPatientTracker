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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.add_doctor.*
import kotlinx.android.synthetic.main.add_doctor.mainView
import kotlinx.android.synthetic.main.add_hospital_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*

class AddHospitalFragment : Fragment() {
    private lateinit var root: View
    private lateinit var addHospitalViewModel: AddHospitalViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var spinnerType: SearchableSpinner
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
    }

    private fun prepareTypeList() {
        typeList.add("COE")
        typeList.add("REFERAL")
        initializeTypeSpinner(spinnerType, typeList)

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

                val type = typeList[position]
                if (type == "COE") {
                    findNavController().navigate(R.id.action_addHospital_coeFragment)
                } else {
                    findNavController().navigate(R.id.action_addHospital_ReferalFragment)

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

        spinnerType = root.findViewById(R.id.typeSpinner)

        mainView.setOnClickListener {
            hideKeyboard()
        }

        /*   email = root.findViewById(R.id.input_email)
           passwordEt = root.findViewById(R.id.input_password)
           mainLayout.setOnClickListener {
               hideKeyboard()
           }*/
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