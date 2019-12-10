package com.philipgurr.smartshoppinglist.ui.detail.addproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import com.philipgurr.smartshoppinglist.databinding.AddProductChoiceItemBinding
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod
import com.philipgurr.smartshoppinglist.ui.BaseListBindingAdapter
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProductChoiceListAdapter(
    private val onClick: (String) -> Unit
) : BaseListBindingAdapter<String, AddProductChoiceItemBinding>() {
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
    ) : ItemViewHolder<String, AddProductChoiceItemBinding>(binding) {
        override fun bind(item: String) {
            with(binding) {
                label = item
                root.onClick { onClick(item) }
            }
        }
    }
}