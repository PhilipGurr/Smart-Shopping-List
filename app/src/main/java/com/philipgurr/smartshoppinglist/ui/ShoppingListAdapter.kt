package com.philipgurr.smartshoppinglist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.ui.fragments.ShoppingListFragmentDirections
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder>() {
    var data = listOf<ShoppingList>()

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
        private val mapper = ShoppingListToUIMapper()

        fun bind(shoppingList: ShoppingList) {
            val shoppingListUI = mapper.map(shoppingList)
            with(view) {
                setText(shoppingListUI)
                setProgress(shoppingListUI)
                setOnClickListener {
                    navigateToDetailScreen(shoppingList)
                }
            }
        }

        private fun View.setText(shoppingListUI: ShoppingListUI) {
            shoppingListName.text = shoppingListUI.name
            shoppingListProgress.text = "${shoppingListUI.progress}/${shoppingListUI.totalProducts}"
        }

        private fun View.setProgress(shoppingListUI: ShoppingListUI) {
            shoppingListProgressBar.max = shoppingListUI.totalProducts
            shoppingListProgressBar.progress = shoppingListUI.progress
            // TODO: Set progressbar color
        }

        private fun navigateToDetailScreen(shoppingList: ShoppingList) {
            val actionDetail = ShoppingListFragmentDirections.actionShoppingListDetail(shoppingList)
            Navigation.findNavController(view).navigate(actionDetail)
        }
    }
}