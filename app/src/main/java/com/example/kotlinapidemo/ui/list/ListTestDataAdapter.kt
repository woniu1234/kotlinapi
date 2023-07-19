package com.example.kotlinapidemo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapidemo.R
import com.example.kotlinapidemo.beans.TestDataBean
import com.example.kotlinapidemo.databinding.ItemListDataLayoutBinding

class ListTestDataAdapter(private val dataList: ArrayList<TestDataBean> = arrayListOf()) :
    RecyclerView.Adapter<ListTestDataAdapter.DataViewHolder>() {


    class DataViewHolder(val binding: ItemListDataLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun addDataList(dataList: List<TestDataBean>) {
        val size = this.dataList.size
        this.dataList.addAll(dataList)
        notifyItemRangeInserted(size, dataList.size)
    }

    fun setDataList(dataList: List<TestDataBean>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyItemRangeChanged(0, this.dataList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemListDataLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.tvData.text = "测试".plus(position)
        holder.binding.root.setBackgroundColor(
            if (position % 2 == 0) {
                ContextCompat.getColor(holder.binding.root.context, R.color.purple_500)
            } else {
                ContextCompat.getColor(holder.binding.root.context, R.color.purple_200)
            }
        )
    }
}