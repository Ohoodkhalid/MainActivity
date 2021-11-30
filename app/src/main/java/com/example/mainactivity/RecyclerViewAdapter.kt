package com.example.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RecyclerViewAdapter(var  message:List<String>,private val item:List<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent,false))
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items = item[position]
        val mess = message[position]

        holder.itemView.apply {
            guessTextView.text = mess +items


        }
    }

    override fun getItemCount() = item.size
}
