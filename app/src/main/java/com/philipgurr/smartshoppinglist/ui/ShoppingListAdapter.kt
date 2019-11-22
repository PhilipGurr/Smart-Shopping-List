package com.philipgurr.smartshoppinglist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.philipgurr.smartshoppinglist.databinding.ShoppinglistItemBinding
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.ui.fragments.ShoppingListFragmentDirections
import kotlinx.android.synthetic.main.shoppinglist_item.view.*

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder>() {
    var data = listOf<ShoppingList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShoppinglistItemBinding.inflate(inflater, parent, false)
        val viewHolder = ItemViewHolder(binding)
        binding.viewHolder = viewHolder
        return viewHolder
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        holderItem.bind(data[position])
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    class ItemViewHolder(private val binding: ShoppinglistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mapper = ShoppingListToUIMapper()

        fun bind(shoppingList: ShoppingList) {
            val shoppingListUI = mapper.map(shoppingList)
            binding.shoppingList = shoppingList
            binding.shoppingListUI = shoppingListUI
            binding.executePendingBindings()
            /*with(view) {
                setText(shoppingListUI)
                setProgress(shoppingListUI)
                setOnClickListener {
                    navigateToDetailScreen(shoppingList)
                }
            }*/
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

        fun navigateToDetailScreen(shoppingList: ShoppingList) {
            val actionDetail = ShoppingListFragmentDirections.actionShoppingListDetail(shoppingList)
            Navigation.findNavController(binding.root).navigate(actionDetail)
        }
    }
}