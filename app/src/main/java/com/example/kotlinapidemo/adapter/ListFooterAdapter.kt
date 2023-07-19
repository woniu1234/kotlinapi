package com.example.kotlinapidemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapidemo.LoadMoreInterface
import com.example.kotlinapidemo.databinding.ItemListFooterLayoutBinding

class ListFooterAdapter : RecyclerView.Adapter<ListFooterAdapter.FooterViewHolder>() {

    var loadMoreInterface: LoadMoreInterface? = null

    class FooterViewHolder(val binding: ItemListFooterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        return FooterViewHolder(
            ItemListFooterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: FooterViewHolder, position: Int) {

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        loadMoreInterface?.loadMore()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

    }
}