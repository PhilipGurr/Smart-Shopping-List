package com.philipgurr.smartshoppinglist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.philipgurr.domain.ShoppingList
import com.philipgurr.smartshoppinglist.databinding.ShoppinglistItemBinding
import com.philipgurr.smartshoppinglist.ui.mylists.MyListsFragmentDirections
import com.philipgurr.smartshoppinglist.ui.util.BaseListBindingAdapter

class ShoppingListsAdapter : BaseListBindingAdapter<ShoppingList, ShoppinglistItemBinding>() {
    override var data = listOf<ShoppingList>()

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ShoppingList, ShoppinglistItemBinding> {
        val binding = ShoppinglistItemBinding.inflate(inflater, parent, false)
        return ShoppingListViewHolder(
            binding
        )
    }

    class ShoppingListViewHolder(private val binding: ShoppinglistItemBinding) :
        ItemViewHolder<ShoppingList, ShoppinglistItemBinding>(binding) {

        override fun bind(item: ShoppingList) {
            with(binding) {
                viewHolder = this@ShoppingListViewHolder
                shoppingList = item
                executePendingBindings()
            }
        }

        fun navigateToDetailScreen(shoppingList: ShoppingList) {
            val actionDetail =
                MyListsFragmentDirections.actionShoppingListDetail(
                    shoppingList
                )
            Navigation.findNavController(binding.root).navigate(actionDetail)
        }
    }
}