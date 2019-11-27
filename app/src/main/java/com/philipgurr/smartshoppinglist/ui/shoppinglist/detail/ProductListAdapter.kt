package com.philipgurr.smartshoppinglist.ui.shoppinglist.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.ProductlistItemBinding
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.ui.ListBindingAdapter

class ProductListAdapter : ListBindingAdapter<Product, ProductlistItemBinding>() {
    override var data = listOf<Product>()

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<Product, ProductlistItemBinding> {
        val binding: ProductlistItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.productlist_item, parent, false)
        return ProductViewHolder(binding)
    }

    class ProductViewHolder(private val binding: ProductlistItemBinding) :
        ItemViewHolder<Product, ProductlistItemBinding>(binding) {

        override fun bind(item: Product) {
            binding.product = item
        }
    }
}