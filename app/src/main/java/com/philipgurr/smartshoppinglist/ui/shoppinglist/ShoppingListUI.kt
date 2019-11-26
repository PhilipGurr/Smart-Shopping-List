package com.philipgurr.smartshoppinglist.ui.shoppinglist

data class ShoppingListUI(
    val name: String,
    val progress: Int,
    val totalProducts: Int,
    val progressBarColor: Int
)