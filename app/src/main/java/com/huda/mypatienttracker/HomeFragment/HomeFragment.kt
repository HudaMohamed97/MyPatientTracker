package com.huda.mypatienttracker.HomeFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.mypatienttracker.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {
    private lateinit var root: View
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loginPreferences: SharedPreferences
    private var Name = ""


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

    @SuppressLint("SetTextI18n")
    private fun setClickListeners() {
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        hospitalCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_HospitalList)
        }
        activity_card.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_toActivityFragment)
        }
        patientCard.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_toPatientFragment)
        }
        notification.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_toNotification)
        }

        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        Name = loginPreferences.getString("Name", "").toString()
        targetText.text = "All Target  " + loginPreferences.getInt(
            "TargetAll",
            0
        ).toString() + "  Not PH  " + loginPreferences.getInt(
            "TargetNotPh",
            0
        ).toString() + "  Confirmed   " +
                loginPreferences.getInt(
                    "TargetConfirmed",
                    0
                ).toString() + "  No Update  " + loginPreferences.getInt(
            "TargetNoUpdate",
            0
        ).toString()

        textView.text = "welcom  " + Name + " ..."

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