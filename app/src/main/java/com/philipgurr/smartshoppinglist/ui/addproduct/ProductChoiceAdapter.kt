package com.philipgurr.smartshoppinglist.ui.addproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import com.philipgurr.smartshoppinglist.databinding.AddProductChoiceItemBinding
import com.philipgurr.smartshoppinglist.ui.ListBindingAdapter

class ProductChoiceAdapter
    : ListBindingAdapter<String, AddProductChoiceItemBinding>() {
    override var data = listOf("Text", "Barcode")

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<String, AddProductChoiceItemBinding> {
        val binding = AddProductChoiceItemBinding.inflate(inflater, parent, false)
        return ChoiceViewHolder(binding)
    }

    class ChoiceViewHolder(private val binding: AddProductChoiceItemBinding)
        : ItemViewHolder<String, AddProductChoiceItemBinding>(binding) {
        override fun bind(item: String) {
            binding.label = item
        }

    }
}