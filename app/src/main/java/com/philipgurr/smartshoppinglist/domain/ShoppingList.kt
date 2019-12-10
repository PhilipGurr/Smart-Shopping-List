package com.philipgurr.smartshoppinglist.domain

import java.io.Serializable
import java.util.*

class ShoppingList(
    var id: String = "",
    var name: String = "",
    val created: Date = Date(),
    var products: List<Product> = listOf()
) : Serializable {
    fun completedProducts() = products.filter { it.completed }
}