package com.philipgurr.smartshoppinglist.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.ProductlistItemBinding
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.ui.BaseListBindingAdapter
import com.philipgurr.smartshoppinglist.vm.ListDetailViewModel

class ProductListAdapter(
    private val viewModel: ListDetailViewModel
) : BaseListBindingAdapter<Product, ProductlistItemBinding>() {
    override var data = listOf<Product>()

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<Product, ProductlistItemBinding> {
        val binding: ProductlistItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.productlist_item, parent, false)
        return ProductViewHolder(binding, viewModel)
    }

    class ProductViewHolder(
        private val binding: ProductlistItemBinding,
        private val viewModelDetail: ListDetailViewModel
    ) : ItemViewHolder<Product, ProductlistItemBinding>(binding) {

        override fun bind(item: Product) = with(binding) {
            product = item
            viewModel = viewModelDetail
            executePendingBindings()
        }
    }
}