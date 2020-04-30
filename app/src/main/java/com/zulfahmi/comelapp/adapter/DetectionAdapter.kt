package com.zulfahmi.comelapp.adapter


import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.model.Detection
import com.zulfahmi.comelapp.util.Commons
import com.zulfahmi.comelapp.util.Constants
import com.zulfahmi.comelapp.util.CustomConfirmDialog
import kotlinx.android.synthetic.main.item_row_detection.view.*

class DetectionAdapter (private val listItem: ArrayList<*>, private val listenter: (Any) -> Unit): RecyclerView.Adapter<DetectionAdapter.ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_detection, parent, false))

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(listItem[position], listenter)
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItem(listType: Any, listener: (Any) -> Unit){
            when (listType) {
                is Detection -> {
                    val date = listType.date
                    val dateConverted = Commons.convertToDate(listType.date)
                    val userId = listType.userId
                    val counter = listType.counter
                    val location = listType.location
                    val responded = listType.responded
                    val message = "$userId detected in $location ($counter)"
                    itemView.btn_respond.setOnClickListener{
                        CustomConfirmDialog(it.context, "Respond", "Respond this detection?"){
                            val updateRespond = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DETECTIONS).child("$date/responded")
                            updateRespond.setValue(true)
                            Toast.makeText(it.context, "Detection Responded", Toast.LENGTH_SHORT).show()
                        }.show()
                    }
                    if (responded) itemView.btn_respond.visibility = View.GONE
                    setText(dateConverted, message)
                }
            }

            itemView.setOnClickListener{listener(listType)}
        }

        private fun setText(date: String, message: String) {
            itemView.tv_date.text = date
            itemView.tv_message.text = message
        }
    }
}