package com.philipgurr.smartshoppinglist.domain

import android.app.ProgressDialog

class ShoppingList(val name: String, val items: List<Product>) {
    fun getCompletedProducts() = items.filter { it.completed }
}