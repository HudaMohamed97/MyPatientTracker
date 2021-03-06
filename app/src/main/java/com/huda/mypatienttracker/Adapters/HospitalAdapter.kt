package com.huda.mypatienttracker.Adapters

import android.content.Context
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
import com.huda.mypatienttracker.Models.HospitalModels.HospitalData


class HospitalAdapter(modelFeedArrayList: ArrayList<HospitalData>) :
    RecyclerView.Adapter<HospitalAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnDotsClickListener
    private var context: Context? = null
    private var fromTab = ""


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<HospitalData>()


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
        holder.hospitalName.text = modelFeed.name
        holder.hospitalLocation.text = modelFeed.city.name
        holder.hospitalAdress.text = modelFeed.country.name
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


                popup.inflate(R.menu.option_menu)


                //adding click listener
                popup.setOnMenuItemClickListener { item ->
                    when {
                        item.itemId == R.id.menu1 -> {
                            fromTab = "AddDoctor"
                            onItemClickListener.onDotsImageClicked(position, fromTab)
                            true
                        }
                        item.itemId == R.id.menu2 -> {
                            fromTab = "Update"
                            onItemClickListener.onDotsImageClicked(position, fromTab)
                            true
                        }
                        item.itemId == R.id.menu3 -> {
                            fromTab = "Delete"
                            onItemClickListener.onDotsImageClicked(position, fromTab)
                            true
                        }
                        item.itemId == R.id.menu5 -> {
                            fromTab = "Details"
                            onItemClickListener.onDotsImageClicked(position, fromTab)
                            true
                        } item.itemId == R.id.menu4 -> {
                            fromTab = "Target"
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

        var hospitalAdress: TextView = itemView.findViewById<View>(R.id.hospitalAdress) as TextView
        var hospitalLocation: TextView =
            itemView.findViewById<View>(R.id.hospitalLocation) as TextView
        var hospitalName: TextView = itemView.findViewById<View>(R.id.hospitalName) as TextView

    }

    interface OnDotsClickListener {
        fun onDotsImageClicked(position: Int, fromTab: String)
    }

    fun setOnCommentListener(onDotsClickListene: OnDotsClickListener) {
        this.onItemClickListener = onDotsClickListene
    }

}
