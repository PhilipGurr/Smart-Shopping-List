package com.philipgurr.smartshoppinglist.domain

import java.io.Serializable
import java.util.*

class ShoppingList(
    var id: String = "",
    val name: String = "",
    val created: Date = Date(),
    var products: List<Product> = listOf()
) : Serializable {
    fun getCompletedProducts() = products.filter { it.completed }
}