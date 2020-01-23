package com.philipgurr.smartshoppinglist.ui.mylists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.philipgurr.domain.ShoppingList
import com.philipgurr.smartshoppinglist.databinding.ShoppinglistItemBinding
import com.philipgurr.smartshoppinglist.ui.util.BaseListBindingAdapter

open class ShoppingListsAdapter : BaseListBindingAdapter<ShoppingList, ShoppinglistItemBinding>() {
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

    open class ShoppingListViewHolder(private val binding: ShoppinglistItemBinding) :
        ItemViewHolder<ShoppingList, ShoppinglistItemBinding>(binding) {

        override fun bind(item: ShoppingList) {
            with(binding) {
                viewHolder = this@ShoppingListViewHolder
                shoppingList = item
                executePendingBindings()
            }
        }

        override fun navigateToDetailScreen(item: ShoppingList) {
            val actionDetail =
                MyListsFragmentDirections.actionShoppingListDetail(
                    item
                )
            Navigation.findNavController(binding.root).navigate(actionDetail)
        }
    }
}