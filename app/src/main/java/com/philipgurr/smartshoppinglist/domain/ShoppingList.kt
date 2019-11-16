package com.philipgurr.smartshoppinglist.domain

class ShoppingList(val name: String, val products: List<Product>) {
    fun getCompletedProducts() = products.filter { it.completed }
}