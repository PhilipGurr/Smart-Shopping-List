package com.philipgurr.smartshoppinglist.ui

import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.util.Mapper
import com.philipgurr.smartshoppinglist.util.ProgressBarColorPicker

class ShoppingListToUIMapper : Mapper<ShoppingList, ShoppingListUI> {

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