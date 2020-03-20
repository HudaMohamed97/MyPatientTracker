package com.huda.mypatienttracker.OpsumitFargment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.add_doctor.*
import kotlinx.android.synthetic.main.login_fragment.*

class OpsumitFragment : Fragment() {
    private lateinit var root: View
    private lateinit var opesumitFragmentViewModel: OpesumitFragmentViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.opsumit_patient, container, false)
        opesumitFragmentViewModel =
            ViewModelProviders.of(this).get(OpesumitFragmentViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
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