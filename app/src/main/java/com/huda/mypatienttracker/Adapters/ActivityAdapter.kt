package com.huda.mypatienttracker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.R
import java.util.*
import androidx.appcompat.view.ContextThemeWrapper


class ActivityAdapter(modelFeedArrayList: ArrayList<String>) :
    RecyclerView.Adapter<ActivityAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnCommentClickListener
    private var context: Context? = null
    private var fromTab = ""


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<String>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_row, parent, false)
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
        holder.dotsImage.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                val wrapper = ContextThemeWrapper(context, R.style.popupMenuStyle)
                val popup = PopupMenu(wrapper, holder.dotsImage)
                //inflating menu from xml resource
                try {
                    val fields = popup.javaClass.declaredFields
                    for (field in fields) {
                        if ("mPopup" == field.name) {
                            field.isAccessible = true
                            val menuPopupHelper = field.get(popup)
                            val classPopupHelper =
                                Class.forName(menuPopupHelper.javaClass.name)
                            val setForceIcons = classPopupHelper.getMethod(
                                "setForceShowIcon",
                                Boolean::class.javaPrimitiveType
                            )
                            setForceIcons.invoke(menuPopupHelper, true)
                            break
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


                popup.inflate(R.menu.activity_menu)


                //adding click listener
                popup.setOnMenuItemClickListener { item ->
                    when {
                        item.itemId == R.id.menu1 -> {
                            fromTab = "AddDoctor"
                            onItemClickListener.onDotsImageClicked(position, fromTab)
                            true
                        }
                        item.itemId == R.id.menu2 -> {
                            fromTab = "update"
                            onItemClickListener.onDotsImageClicked(position, fromTab)
                            true
                        }
                        item.itemId == R.id.menu3 -> {
                            fromTab = "delete"
                            onItemClickListener.onDotsImageClicked(position, fromTab)
                            true
                        }
                    }
                    true
                }
                //displaying the popup
                popup.show()
            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dotsImage: TextView = itemView.findViewById(R.id.textViewOptions)

        /*  var tvName: TextView
          var tvTime: TextView
          var tvStatus: TextView
          var imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
          var root = itemView.findViewById<View>(R.id.root)

          init {
              tvName = itemView.findViewById<View>(R.id.tv_name) as TextView
              tvTime = itemView.findViewById<View>(R.id.tv_time) as TextView
              tvStatus = itemView.findViewById<View>(R.id.tv_status) as TextView
          }*/
    }

    interface OnCommentClickListener {
        fun onDotsImageClicked(position: Int, fromTab: String)
    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onItemClickListener = onCommentClickListener
    }

}
