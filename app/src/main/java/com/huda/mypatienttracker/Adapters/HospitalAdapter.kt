package com.imagin.myapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.Models.hosapitalData
import com.huda.mypatienttracker.R
import java.util.*


class HospitalAdapter(modelFeedArrayList: ArrayList<hosapitalData>) :
    RecyclerView.Adapter<HospitalAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnCommentClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<hosapitalData>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.hospital_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
/*
        holder.tvName.text = modelFeed.owner.name
        holder.tvStatus.text = modelFeed.content
        holder.tvTime.text = modelFeed.created_at
        if (modelFeed.owner.photo != null) {
            Glide.with(context!!).load(modelFeed.owner.photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(holder.imgProfile)
        }
        if (modelFeed.photo == null) {
            holder.imgviewPostpic.visibility = View.GONE
        } else {
            holder.imgviewPostpic.visibility = View.VISIBLE
            Glide.with(context!!).load(modelFeed.photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(holder.imgviewPostpic)

        }*/
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onPostClicked(position)
            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

      /*  var tvName: TextView
        var tvTime: TextView
        var tvStatus: TextView
        var imgviewPostpic = itemView.findViewById<ImageView>(R.id.imgView_postPic)
        var imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
        var root = itemView.findViewById<View>(R.id.root)

        init {
            tvName = itemView.findViewById<View>(R.id.tv_name) as TextView
            tvTime = itemView.findViewById<View>(R.id.tv_time) as TextView
            tvStatus = itemView.findViewById<View>(R.id.tv_status) as TextView
        }*/
    }

    interface OnCommentClickListener {
        fun onPostClicked(position: Int)
        fun onCommentClicked(userModel: hosapitalData, position: Int)
        fun onLikeClicked(userModel: hosapitalData, position: Int)

    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onItemClickListener = onCommentClickListener
    }

}
