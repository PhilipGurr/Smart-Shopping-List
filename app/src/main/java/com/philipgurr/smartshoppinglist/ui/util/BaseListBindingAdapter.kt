package com.philipgurr.smartshoppinglist.ui.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListBindingAdapter<T, B : ViewDataBinding>
    : RecyclerView.Adapter<BaseListBindingAdapter.ItemViewHolder<T, B>>() {
    abstract var data: List<T>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder<T, B> {
        val inflater = LayoutInflater.from(parent.context)
        return createViewHolder(inflater, parent)
    }

    abstract fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): ItemViewHolder<T, B>

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemViewHolder<T, B>, position: Int) {
        holder.bind(data[position])
    }

    fun removeItem(position: Int) {
        val newItems = data.toMutableList()
        newItems.removeAt(position)
        data = newItems
    }

    abstract class ItemViewHolder<T, B : ViewDataBinding>(
        binding: B
    ) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)
    }
}