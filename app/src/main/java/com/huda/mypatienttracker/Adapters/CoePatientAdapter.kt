package com.huda.mypatienttracker.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.huda.mypatienttracker.R
import java.util.*
import androidx.appcompat.view.ContextThemeWrapper
import com.huda.mypatienttracker.Models.PatientResponseData


class CoePatientAdapter(modelFeedArrayList: ArrayList<PatientResponseData>) :
    RecyclerView.Adapter<CoePatientAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnCommentClickListener
    private var context: Context? = null
    private var fromTab = ""


    override fun getItemCount(): Int {
        Log.i("hhhhhh", "in adaptern hena" + modelFeedArrayList.size)
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<PatientResponseData>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coe_patient_row_list, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
        holder.hospitalNameCoe.text = modelFeed.hospital?.name
        if (modelFeed.doctor.name != null) {
            holder.doctorName.text = modelFeed.doctor.name
        }
        holder.time.text = modelFeed.created_at
        if (modelFeed.last_treatment == null) {
            holder.patient_type.text = "No etiology"
            holder.product.text = "No Treatment"
        } else {
            holder.product.text = modelFeed.last_treatment.type_medication
            holder.patient_type.text = modelFeed.last_treatment.etiology
        }

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


                popup.inflate(R.menu.update_menu)

                popup.setOnMenuItemClickListener { item ->
                    when {
                        item.itemId == R.id.menu1 -> {
                            fromTab = "update"
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

        var dotsImage: ImageView = itemView.findViewById(R.id.textViewOptions)
        var hospitalNameCoe: TextView =
            itemView.findViewById<View>(R.id.hospitalNameCoe) as TextView
        var doctorName: TextView = itemView.findViewById<View>(R.id.doctor) as TextView
        var patient_type: TextView = itemView.findViewById<View>(R.id.patient_type) as TextView
        var product: TextView = itemView.findViewById<View>(R.id.product) as TextView
        var time: TextView = itemView.findViewById<View>(R.id.timeCoe) as TextView
        var type: TextView = itemView.findViewById<View>(R.id.type) as TextView


    }

    interface OnCommentClickListener {
        fun onDotsImageClicked(position: Int, fromTab: String)
    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onItemClickListener = onCommentClickListener
    }

}
