package com.example.kotlinapidemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapidemo.R
import com.example.kotlinapidemo.databinding.ItemListHeaderLayoutBinding

class ListHeaderAdapter :
    RecyclerView.Adapter<ListHeaderAdapter.HeaderViewHolder>() {

    class HeaderViewHolder(val binding: ItemListHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            ItemListHeaderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.binding.imageHeader.setBackgroundColor(
            ContextCompat.getColor(
                holder.binding.imageHeader.context,
                R.color.purple_500
            )
        )
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

}