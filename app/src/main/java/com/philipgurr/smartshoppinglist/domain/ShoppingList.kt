package com.philipgurr.smartshoppinglist.domain

import java.io.Serializable

class ShoppingList(
    var id: String = "",
    val name: String = "",
    var products: List<Product> = listOf()
) : Serializable {
    fun getCompletedProducts() = products.filter { it.completed }
}