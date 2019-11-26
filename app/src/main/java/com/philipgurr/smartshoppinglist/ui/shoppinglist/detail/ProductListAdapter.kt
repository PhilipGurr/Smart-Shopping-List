package com.philipgurr.smartshoppinglist.ui.shoppinglist.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.ProductlistItemBinding
import com.philipgurr.smartshoppinglist.domain.Product

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ItemViewHolder>() {

    var data = listOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ProductlistItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.productlist_item, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        holderItem.bind(data[position])
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    class ItemViewHolder(private val binding: ProductlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
        }

        // TODO: Implement OnClick logic
    }
}