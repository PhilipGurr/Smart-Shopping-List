package com.philipgurr.smartshoppinglist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.philipgurr.smartshoppinglist.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ShoppingListAdapter() : RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder>() {

    var data = listOf<ShoppingListUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = parent.inflate(R.layout.recyclerview_item)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        holderItem.bind(data[position])
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(shoppingListUI: ShoppingListUI) {
            with(shoppingListUI) {
                setText()
                setProgress()
            }
        }

        private fun ShoppingListUI.setText() {
            view.shoppingListName.text = name
            view.shoppingListProgress.text = "${progress}/${totalProducts}"
        }

        private fun ShoppingListUI.setProgress() {
            view.shoppingListProgressBar.max = totalProducts
            view.shoppingListProgressBar.progress = progress
        }
    }
}