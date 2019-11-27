package com.philipgurr.smartshoppinglist.ui.shoppinglist.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.philipgurr.smartshoppinglist.databinding.ShoppinglistItemBinding
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.ui.ListBindingAdapter
import com.philipgurr.smartshoppinglist.ui.shoppinglist.ShoppingListToUIMapper

class ShoppingListAdapter : ListBindingAdapter<ShoppingList, ShoppinglistItemBinding>() {
    override var data = listOf<ShoppingList>()

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ShoppingList, ShoppinglistItemBinding> {
        val binding = ShoppinglistItemBinding.inflate(inflater, parent, false)
        return ShoppingListViewHolder(binding)
    }

    class ShoppingListViewHolder(private val binding: ShoppinglistItemBinding) :
        ItemViewHolder<ShoppingList, ShoppinglistItemBinding>(binding) {
        private val mapper = ShoppingListToUIMapper()

        override fun bind(item: ShoppingList) {
            val uiModel = mapper.map(item)
            with(binding) {
                viewHolder = this@ShoppingListViewHolder
                shoppingList = item
                shoppingListUI = uiModel
                executePendingBindings()
            }
        }

        fun navigateToDetailScreen(shoppingList: ShoppingList) {
            val actionDetail = ShoppingListFragmentDirections.actionShoppingListDetail(shoppingList)
            Navigation.findNavController(binding.root).navigate(actionDetail)
        }
    }
}