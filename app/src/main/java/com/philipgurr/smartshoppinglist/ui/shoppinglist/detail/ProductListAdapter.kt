package com.philipgurr.smartshoppinglist.ui.shoppinglist.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.domain.Product
import kotlinx.android.synthetic.main.productlist_item.view.*

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ItemViewHolder>() {

    var data = listOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = parent.inflate(R.layout.productlist_item)
        return ItemViewHolder(
            view
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        holderItem.bind(data[position])
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            with(view) {
                productName.text = product.name
                completedCheckBox.isChecked = product.completed
            }
        }

        // TODO: Implement OnClick logic
    }
}