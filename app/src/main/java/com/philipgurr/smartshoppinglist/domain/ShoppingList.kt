package com.philipgurr.smartshoppinglist.domain

class ShoppingList(
    var id: String = "",
    val name: String = "",
    var products: List<Product> = listOf()
) {
    fun getCompletedProducts() = products.filter { it.isCompleted }
}