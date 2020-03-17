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
import com.huda.mypatienttracker.Models.PatientResponse
import com.huda.mypatienttracker.Models.PatientResponseData


class PatientAdapter(modelFeedArrayList: ArrayList<PatientResponseData>) :
    RecyclerView.Adapter<PatientAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnCommentClickListener
    private var context: Context? = null
    private var fromTab = ""


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<PatientResponseData>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.patient_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
        holder.tvName.text = modelFeed.hospital.name

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

        var tvName: TextView
        /*  var tvTime: TextView
          var tvStatus: TextView*/

        init {
            tvName = itemView.findViewById<View>(R.id.hospitalName) as TextView
            /* tvTime = itemView.findViewById<View>(R.id.tv_time) as TextView
             tvStatus = itemView.findViewById<View>(R.id.tv_status) as TextView*/
        }
    }

    interface OnCommentClickListener {
        fun onDotsImageClicked(position: Int, fromTab: String)
    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onItemClickListener = onCommentClickListener
    }

}
