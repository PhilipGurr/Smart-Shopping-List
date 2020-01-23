package com.philipgurr.smartshoppinglist.ui.completedlists

import androidx.navigation.Navigation
import com.philipgurr.domain.ShoppingList
import com.philipgurr.smartshoppinglist.databinding.ShoppinglistItemBinding
import com.philipgurr.smartshoppinglist.ui.mylists.ShoppingListsAdapter

class CompletedListsAdapter : ShoppingListsAdapter() {
    class CompletedListsViewHolder(private val binding: ShoppinglistItemBinding) :
        ShoppingListViewHolder(binding) {

        override fun bind(item: ShoppingList) {
            with(binding) {
                viewHolder = this@CompletedListsViewHolder
                shoppingList = item
                executePendingBindings()
            }
        }

        override fun navigateToDetailScreen(item: ShoppingList) {
            val actionDetail = CompletedListsFragmentDirections.actionShoppingListDetail(
                item
            )
            Navigation.findNavController(binding.root).navigate(actionDetail)
        }
    }
}