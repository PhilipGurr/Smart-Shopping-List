package com.philipgurr.smartshoppinglist.ui.addproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import com.philipgurr.smartshoppinglist.databinding.AddProductChoiceItemBinding
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod
import com.philipgurr.smartshoppinglist.ui.ListBindingAdapter
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProductChoiceAdapter(
    private val onClick: (String) -> Unit
) : ListBindingAdapter<String, AddProductChoiceItemBinding>() {
    override var data = listOf(
        ProductInputMethod.TEXT_INPUT_METHOD,
        ProductInputMethod.BARCODE_INPUT_METHOD
    )

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<String, AddProductChoiceItemBinding> {
        val binding = AddProductChoiceItemBinding.inflate(inflater, parent, false)
        return ChoiceViewHolder(onClick, binding)
    }

    class ChoiceViewHolder(
        private val onClick: (String) -> Unit,
        private val binding: AddProductChoiceItemBinding
    ) :
        ItemViewHolder<String, AddProductChoiceItemBinding>(binding) {
        override fun bind(item: String) {
            with(binding) {
                label = item
                root.onClick { onClick(item) }
            }
        }
    }
}