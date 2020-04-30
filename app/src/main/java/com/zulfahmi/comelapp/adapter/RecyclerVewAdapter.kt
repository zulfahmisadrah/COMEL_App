package com.zulfahmi.comelapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.model.Detection
import com.zulfahmi.comelapp.model.Notification
import com.zulfahmi.comelapp.model.User
import kotlinx.android.synthetic.main.item_row_notification.view.*

class RecyclerViewAdapter (private val listItem: ArrayList<*>, private val listenter: (Any) -> Unit): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_notification, parent, false))

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(listItem[position], listenter)
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItem(listType: Any, listener: (Any) -> Unit){
            when (listType) {
                is Notification -> {

                }
                is User -> {

                }

            }
            itemView.setOnClickListener{listener(listType)}
        }

        private fun setText(nama: String, keterangan: String, firstLetter: String) {
            itemView.tv_date.text = nama
            itemView.tv_message.text = keterangan
            itemView.tv_first_letter.text = firstLetter
        }
    }
}