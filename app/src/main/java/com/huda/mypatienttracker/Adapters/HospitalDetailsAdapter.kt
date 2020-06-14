package com.huda.mypatienttracker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.R
import java.util.*
import com.huda.mypatienttracker.Models.Doctors


class HospitalDetailsAdapter(modelFeedArrayList: ArrayList<Doctors>) :
    RecyclerView.Adapter<HospitalDetailsAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnDotsClickListener
    private var context: Context? = null
    private var fromTab = ""


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<Doctors>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
        holder.doctorName.text = modelFeed.name
        holder.doctorSpeciality.text = modelFeed.speciality
        holder.doctorType.text = modelFeed.type

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var doctorType = itemView.findViewById<TextView>(R.id.doctorType)
        var doctorSpeciality = itemView.findViewById<TextView>(R.id.doctorSpeciality)
        var doctorName = itemView.findViewById<TextView>(R.id.DoctorName)

    }

    interface OnDotsClickListener {
        fun onDotsImageClicked(position: Int, fromTab: String)
    }

    fun setOnCommentListener(onDotsClickListene: OnDotsClickListener) {
        this.onItemClickListener = onDotsClickListene
    }

}
