package com.example.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item1.view.*

class RecyclerViewAdapterr(var guessText:ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapterr.ItemViewHolder>() {

        class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item1, parent, false)
            )
        }


        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val textt = guessText[position]
            holder.itemView.apply {
                tvText.text = textt
            }
        }

        override fun getItemCount() = guessText.size

    fun updateAdapter( newGuessList:ArrayList<String>)
    {
        guessText = newGuessList
        notifyDataSetChanged()
    }

    }

