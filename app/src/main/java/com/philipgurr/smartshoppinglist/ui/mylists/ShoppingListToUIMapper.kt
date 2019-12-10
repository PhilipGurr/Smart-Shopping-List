package com.philipgurr.smartshoppinglist.ui.mylists

import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.util.Mapper
import com.philipgurr.smartshoppinglist.util.ProgressBarColorPicker

class ShoppingListToUIMapper : Mapper<ShoppingList, ShoppingListUI> {

    private fun ShoppingList.getCompletedProducts() = products.filter { it.completed }

    override fun map(item: ShoppingList): ShoppingListUI = with(item) {
        val color = ProgressBarColorPicker.choose(getCompletedProducts().size, products.size)

        ShoppingListUI(
            name,
            getCompletedProducts().size,
            products.size,
            color
        )
    }
}