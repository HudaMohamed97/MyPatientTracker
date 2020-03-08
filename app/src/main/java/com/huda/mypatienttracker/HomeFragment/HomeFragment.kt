package com.huda.mypatienttracker.HomeFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.catapplication.utilies.Validation
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*

class HomeFragment : Fragment() {
    private lateinit var root: View
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var email: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.home_fragment, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        hospitalCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_HospitalList)
        }
        activity_card.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_toActivityFragment)
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

    private fun checkErrorEnabled() {
        if (!Validation.validate(email.text.toString())) {
            Toast.makeText(activity, "empty Email please fill it", Toast.LENGTH_SHORT).show()
        } else if (!Validation.validateEmail(email.text.toString())) {
            Toast.makeText(
                activity,
                "Invalid Email Format Please enter valid mail",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            if (!Validation.validate(passwordEt.text.toString())) {
                Toast.makeText(activity, "empty password please fill it", Toast.LENGTH_SHORT).show()
            }
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