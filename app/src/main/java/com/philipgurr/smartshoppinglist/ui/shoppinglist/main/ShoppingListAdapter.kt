package com.philipgurr.smartshoppinglist.ui.shoppinglist.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.philipgurr.smartshoppinglist.databinding.ShoppinglistItemBinding
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.ui.shoppinglist.ShoppingListToUIMapper
import com.philipgurr.smartshoppinglist.ui.fragments.ShoppingListFragmentDirections

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder>() {
    var data = listOf<ShoppingList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShoppinglistItemBinding.inflate(inflater, parent, false)
        val viewHolder =
            ItemViewHolder(
                binding
            )
        binding.viewHolder = viewHolder
        return viewHolder
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        holderItem.bind(data[position])
    }

    class ItemViewHolder(private val binding: ShoppinglistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mapper =
            ShoppingListToUIMapper()

        fun bind(shoppingList: ShoppingList) {
            val shoppingListUI = mapper.map(shoppingList)
            binding.shoppingList = shoppingList
            binding.shoppingListUI = shoppingListUI
            binding.executePendingBindings()
        }

        fun navigateToDetailScreen(shoppingList: ShoppingList) {
            val actionDetail = ShoppingListFragmentDirections.actionShoppingListDetail(shoppingList)
            Navigation.findNavController(binding.root).navigate(actionDetail)
        }
    }
}