package com.philipgurr.domain

import java.util.*

class ShoppingList(
    var id: String = "",
    var name: String = "",
    val created: Date = Date(),
    var products: MutableList<Product> = mutableListOf()
)