package com.huda.mypatienttracker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.R
import java.util.*
import com.huda.mypatienttracker.Models.SpeakerRequestModel


class CustomSheetActivityAdapter(modelFeedArrayList: ArrayList<SpeakerRequestModel>) :
    RecyclerView.Adapter<CustomSheetActivityAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnCommentClickListener
    private var context: Context? = null
    private var fromTab = ""


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<SpeakerRequestModel>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_custom_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
        holder.speakerName.text = modelFeed.name
        holder.speakerType.text = modelFeed.speaker_type
        holder.deleteIcon.setOnClickListener {
            onItemClickListener.onDeleteImageClicked(position)
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)
        var speakerName = itemView.findViewById<View>(R.id.speakerName) as TextView
        var speakerType = itemView.findViewById<View>(R.id.speakerType) as TextView

    }

    interface OnCommentClickListener {
        fun onDeleteImageClicked(position: Int)
    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onItemClickListener = onCommentClickListener
    }

}
